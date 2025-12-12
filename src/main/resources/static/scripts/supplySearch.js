class ProductSearch {
  stateClasses = {
    show: `show`,
    hidden: `hidden`,
  }

  constructor(index) {
    this.index = index
    this.searchInput = document.getElementById(`productSearch_${index}`)
    this.hiddenInput = document.getElementById(`productId_${index}`)
    this.dropdown = document.getElementById(`productDropdown_${index}`)

    if (!this.searchInput || !this.hiddenInput || !this.dropdown) {
      console.error(`Elements not found for product search ${index}`)
      return
    }

    this.emptyMessage = this.dropdown.querySelector('.empty-message')

    // Получаем только опции с data-value, исключая empty-message
    this.options = Array.from(this.dropdown.querySelectorAll('.custom-select-option'))
      .filter(option => !option.classList.contains('empty-message'))

    this.bindEvents()
  }

  // Получить список уже выбранных товаров на странице (кроме текущего)
  getSelectedProducts() {
    const selectedProducts = []
    const allSearchInputs = document.querySelectorAll('.product-search')

    allSearchInputs.forEach((input) => {
      // Пропускаем текущее поле
      if (input.id === this.searchInput.id) {
        return
      }

      // Извлекаем индекс из id элемента (productSearch_0 -> 0)
      const match = input.id.match(/productSearch_(\d+)/)
      if (!match) return

      const realIndex = parseInt(match[1], 10)

      // Получаем скрытое поле с ID товара
      const hiddenInput = document.getElementById(`productId_${realIndex}`)
      if (hiddenInput && hiddenInput.value) {
        // Находим соответствующую опцию чтобы получить название
        const dropdown = document.getElementById(`productDropdown_${realIndex}`)
        if (dropdown) {
          const selectedOption = dropdown.querySelector(`[data-value="${hiddenInput.value}"]`)
          if (selectedOption) {
            const productName = selectedOption.getAttribute('data-name') || selectedOption.textContent.trim()
            selectedProducts.push(productName.toLowerCase())
          }
        }
      }
    })

    return selectedProducts
  }

  showNoResultsMessage() {
    this.emptyMessage.classList.add(this.stateClasses.show)
  }

  removeNoResultsMessage() {
    this.emptyMessage.classList.remove(this.stateClasses.show)
  }

  filterOptions(searchTerm) {
    let hasVisibleOptions = false
    const selectedProducts = this.getSelectedProducts()

    this.options.forEach(option => {
      const text = option.textContent.toLowerCase().trim()
      const productName = (option.getAttribute('data-name') || text).toLowerCase()

      // Проверяем совпадение с поисковым запросом
      const matchesSearch = text.includes(searchTerm)

      // Проверяем, не выбран ли уже этот товар в другом поле
      const isAlreadySelected = selectedProducts.includes(productName)

      if (matchesSearch && !isAlreadySelected) {
        option.style.display = 'block'
        hasVisibleOptions = true
      } else {
        option.style.display = 'none'
      }
    })

    if (!hasVisibleOptions && searchTerm) {
      this.showNoResultsMessage()
    } else {
      this.removeNoResultsMessage()
    }
  }

  onFocusSearchInput() {
    this.dropdown.classList.add(this.stateClasses.show)
    this.filterOptions('')
  }

  onInputSearchInput() {
    const searchTerm = this.searchInput.value.toLowerCase()
    this.filterOptions(searchTerm)
  }

  onOptionClick(e) {
    const value = e.currentTarget.getAttribute('data-value')
    const text = e.currentTarget.textContent.trim()

    if (value) {
      this.hiddenInput.value = value
      this.searchInput.value = text
      this.dropdown.classList.remove(this.stateClasses.show)

      // Обновляем все другие поля поиска, чтобы исключить выбранный товар
      this.updateOtherSearches()
    }
  }

  // Обработчик очистки поля
  onClearInput() {
    if (this.searchInput.value === '') {
      this.hiddenInput.value = ''
      // Триггерим событие для обновления других полей и состояния кнопки
      document.dispatchEvent(new CustomEvent('productSelected', {
        detail: { sourceIndex: this.index }
      }))
    }
  }

  // Обновить фильтры в других полях поиска
  updateOtherSearches() {
    // Триггерим событие для обновления других экземпляров
    document.dispatchEvent(new CustomEvent('productSelected', {
      detail: { sourceIndex: this.index }
    }))
  }

  // Обработчик события выбора товара в другом поле
  onProductSelectedElsewhere() {
    // Если dropdown открыт, перефильтруем опции
    if (this.dropdown.classList.contains(this.stateClasses.show)) {
      const searchTerm = this.searchInput.value.toLowerCase()
      this.filterOptions(searchTerm)
    }
  }

  onDocumentClick(e) {
    if (!this.searchInput.contains(e.target) && !this.dropdown.contains(e.target)) {
      this.dropdown.classList.remove(this.stateClasses.show)
    }
  }

  bindEvents() {
    this.searchInput.addEventListener('focus', () => this.onFocusSearchInput())
    this.searchInput.addEventListener('input', () => {
      this.onInputSearchInput()
      this.onClearInput()
    })
    this.options.forEach((option) =>
      option.addEventListener('click', (e) => this.onOptionClick(e))
    )

    document.addEventListener('click', e => this.onDocumentClick(e))

    // Слушаем события выбора товара в других полях
    document.addEventListener('productSelected', (e) => {
      if (e.detail.sourceIndex !== this.index) {
        this.onProductSelectedElsewhere()
      }
    })
  }
}

// Менеджер для управления всеми полями поиска товаров
class SupplyManager {
  constructor() {
    this.productSearchInstances = []
    this.supplyList = document.querySelector('.supply__list')
    this.addButton = document.querySelector('.button--add')
    this.form = document.querySelector('.supply__form')
    this.nextIndex = 0
    this.totalProducts = 0

    this.init()
  }

  init() {
    // Инициализируем существующие поля
    const existingSearchInputs = document.querySelectorAll('.product-search')
    existingSearchInputs.forEach((input) => {
      // Извлекаем реальный индекс из id элемента
      const match = input.id.match(/productSearch_(\d+)/)
      if (!match) return

      const realIndex = parseInt(match[1], 10)
      const instance = new ProductSearch(realIndex)
      this.productSearchInstances.push(instance)
      this.nextIndex = Math.max(this.nextIndex, realIndex + 1)
    })

    // Получаем общее количество товаров из первого dropdown
    const firstDropdown = document.querySelector('.custom-select-dropdown')
    if (firstDropdown) {
      const options = firstDropdown.querySelectorAll('.custom-select-option:not(.empty-message)')
      this.totalProducts = options.length
    }

    // Привязываем событие к кнопке добавления
    if (this.addButton) {
      this.addButton.addEventListener('click', () => this.addNewSupplyItem())
    }

    // Привязываем событие submit к форме
    if (this.form) {
      this.form.addEventListener('submit', (e) => this.handleSubmit(e))
    }

    // Проверяем состояние кнопки при загрузке
    this.updateAddButtonState()

    // Слушаем события выбора товара для обновления состояния кнопки
    document.addEventListener('productSelected', () => this.updateAddButtonState())
  }

  // Создание HTML для нового элемента списка
  createSupplyItemHTML(index) {
    const li = document.createElement('li')
    li.className = 'supply__item'

    // Получаем список продуктов из первого dropdown для копирования
    const firstDropdown = document.querySelector('.custom-select-dropdown')
    let productOptionsHTML = ''

    if (firstDropdown) {
      const options = firstDropdown.querySelectorAll('.custom-select-option:not(.empty-message)')
      options.forEach(option => {
        const value = option.getAttribute('data-value')
        const name = option.getAttribute('data-name')
        const text = option.textContent.trim()

        productOptionsHTML += `
          <div
            class="custom-select-option"
            data-value="${value}"
            data-name="${name || text}"
          >${text}</div>
        `
      })
    }

    li.innerHTML = `
      <div class="custom-select-wrapper custom-select-wrapper--wide">
        <input
          type="text"
          id="productSearch_${index}"
          class="input custom-select-search product-search"
          placeholder="Поиск товара..."
          autocomplete="off"
        >
        <input
          type="hidden"
          id="productId_${index}"
          name="productId"
        >
        <div
          class="custom-select-dropdown"
          id="productDropdown_${index}"
        >
          ${productOptionsHTML}
          <div class="custom-select-option custom-select-option--empty-message empty-message">Ничего не найдено</div>
        </div>
      </div>
      <input
        type="number"
        id="productQuantity_${index}"
        name="quantity"
        class="input product-quantity"
        placeholder="Введите количество"
        min="1"
        step="1"
        autocomplete="off"
      />
      <button
        type="button"
        class="button button--remove"
        onclick="supplyManager.removeSupplyItem(${index})"
      >-</button>
    `

    return li
  }

  // Добавление нового элемента поставки
  addNewSupplyItem() {
    // Проверяем, можем ли добавить еще товары
    const selectedCount = this.getSelectedProductsCount()
    if (selectedCount >= this.totalProducts) {
      return
    }

    const newItem = this.createSupplyItemHTML(this.nextIndex)
    this.supplyList.appendChild(newItem)

    // Создаем новый экземпляр ProductSearch для нового поля
    const newInstance = new ProductSearch(this.nextIndex)
    this.productSearchInstances.push(newInstance)

    this.nextIndex++
    this.updateAddButtonState()
  }

  // Удаление элемента поставки
  removeSupplyItem(index) {
    // Проверяем, чтобы оставался хотя бы один элемент
    const allItems = document.querySelectorAll('.supply__item')
    if (allItems.length <= 1) {
      alert('Должен остаться хотя бы один товар в поставке')
      return
    }

    const item = document.getElementById(`productSearch_${index}`)?.closest('.supply__item')
    if (item) {
      item.remove()

      // Удаляем экземпляр из массива
      this.productSearchInstances = this.productSearchInstances.filter(
        instance => instance.index !== index
      )

      // Обновляем все поля поиска
      document.dispatchEvent(new CustomEvent('productSelected', {
        detail: { sourceIndex: -1 }
      }))

      this.updateAddButtonState()
    }
  }

  // Получить количество выбранных товаров
  getSelectedProductsCount() {
    let count = 0
    const allHiddenInputs = document.querySelectorAll('[id^="productId_"]')

    allHiddenInputs.forEach(input => {
      if (input.value) {
        count++
      }
    })

    return count
  }

  // Собрать данные из формы
  collectFormData() {
    const supplyData = {}
    const allItems = document.querySelectorAll('.supply__item')

    allItems.forEach((item) => {
      // Получаем скрытое поле с ID товара
      const hiddenInput = item.querySelector('[id^="productId_"]')
      // Получаем поле с количеством
      const quantityInput = item.querySelector('.product-quantity')

      if (hiddenInput && hiddenInput.value && quantityInput && quantityInput.value) {
        const productId = hiddenInput.value
        const quantity = parseInt(quantityInput.value, 10)

        // Добавляем в объект: ключ - id товара, значение - количество
        supplyData[productId] = quantity
      }
    })

    return supplyData
  }

  // Обработка отправки формы
  handleSubmit(e) {
    e.preventDefault()

    // Собираем данные
    const supplyData = this.collectFormData()

    // Проверяем что есть хотя бы один товар
    if (Object.keys(supplyData).length === 0) {
      alert('Добавьте хотя бы один товар с количеством')
      return
    }

    // Валидация: все поля должны быть заполнены
    const allItems = document.querySelectorAll('.supply__item')
    let hasEmptyFields = false

    allItems.forEach((item) => {
      const hiddenInput = item.querySelector('[id^="productId_"]')
      const quantityInput = item.querySelector('.product-quantity')

      if (!hiddenInput.value || !quantityInput.value) {
        hasEmptyFields = true
      }
    })

    if (hasEmptyFields) {
      alert('Заполните все поля: товар и количество')
      return
    }

    console.log('Отправляемые данные:', supplyData)

    // Отправляем данные на бэкенд
    this.submitToBackend(supplyData)
  }

  // Отправка данных на бэкенд
  submitToBackend(supplyData) {
    // Получаем URL из action формы
    const actionUrl = this.form.getAttribute('action')

    // Получаем CSRF токен из формы
    const csrfToken = this.form.querySelector('input[name="_csrf"]')?.value

    // Формируем данные в формате application/x-www-form-urlencoded
    const formData = new URLSearchParams()

    // Добавляем CSRF токен первым
    if (csrfToken) {
      formData.append('_csrf', csrfToken)
    }

    // Добавляем данные о товарах как Map (productsId[productId]=quantity)
    for (const [productId, quantity] of Object.entries(supplyData)) {
      formData.append(`productsId[${productId}]`, quantity)
    }

    fetch(actionUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: formData.toString()
    })
    .then(response => {
      if (response.ok) {
        // Перенаправляем пользователя на страницу с поставками
        window.location.href = "/warehouse-manager/dashboard?table=supplies"
      } else {
        return response.text().then(text => {
          throw new Error(text || 'Ошибка при создании поставки')
        })
      }
    })
    .catch(error => {
      console.error('Ошибка:', error)
    })
  }

  // Обновить состояние кнопки добавления
  updateAddButtonState() {
    if (!this.addButton) return

    const selectedCount = this.getSelectedProductsCount()

    if (selectedCount >= this.totalProducts) {
      this.addButton.disabled = true
      this.addButton.style.opacity = '0.5'
      this.addButton.style.cursor = 'not-allowed'
    } else {
      this.addButton.disabled = false
      this.addButton.style.opacity = '1'
      this.addButton.style.cursor = 'pointer'
    }
  }
}

// Инициализация менеджера поставок
let supplyManager
document.addEventListener('DOMContentLoaded', () => {
  supplyManager = new SupplyManager()
})
