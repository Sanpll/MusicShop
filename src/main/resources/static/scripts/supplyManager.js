// Менеджер для управления поставками товаров
// Управляет добавлением/удалением полей, валидацией и отправкой формы
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
        this.initExistingFields()
        this.bindEvents()
        this.updateAddButtonState()
    }

    // Инициализация существующих полей на странице
    initExistingFields() {
        document.querySelectorAll('.product-search').forEach((input) => {
            const match = input.id.match(/productSearch_(\d+)/)
            if (!match) return

            const realIndex = parseInt(match[1], 10)
            const instance = new ProductSearch(realIndex)
            this.productSearchInstances.push(instance)
            this.nextIndex = Math.max(this.nextIndex, realIndex + 1)
        })

        // Получаем общее количество товаров
        const firstDropdown = document.querySelector('.custom-select-dropdown')
        if (firstDropdown) {
            this.totalProducts = firstDropdown.querySelectorAll('.custom-select-option:not(.empty-message)').length
        }
    }

    // Создание HTML для нового элемента поставки
    createSupplyItemHTML(index) {
        const li = document.createElement('li')
        li.className = 'supply__item'

        const firstDropdown = document.querySelector('.custom-select-dropdown')
        let productOptionsHTML = ''

        if (firstDropdown) {
            firstDropdown.querySelectorAll('.custom-select-option:not(.empty-message)').forEach(option => {
                const value = option.getAttribute('data-value')
                const name = option.getAttribute('data-name')
                const text = option.textContent.trim()

                productOptionsHTML += `
          <div class="custom-select-option" data-value="${value}" data-name="${name || text}">${text}</div>
        `
            })
        }

        li.innerHTML = `
      <div class="custom-select-wrapper custom-select-wrapper--wide">
        <input type="text" id="productSearch_${index}" class="input custom-select-search product-search"
               placeholder="Поиск товара..." autocomplete="off">
        <input type="hidden" id="productId_${index}" name="productId">
        <div class="custom-select-dropdown" id="productDropdown_${index}">
          ${productOptionsHTML}
          <div class="custom-select-option custom-select-option--empty-message empty-message">Ничего не найдено</div>
        </div>
      </div>
      <input type="number" id="productQuantity_${index}" name="quantity" class="input product-quantity"
             placeholder="Введите количество" min="1" step="1" autocomplete="off" />
      <button type="button" class="button button--remove" onclick="supplyManager.removeSupplyItem(${index})">-</button>
    `

        return li
    }

    // Добавление нового элемента поставки
    addNewSupplyItem() {
        if (this.getSelectedProductsCount() >= this.totalProducts) return

        const newItem = this.createSupplyItemHTML(this.nextIndex)
        this.supplyList.appendChild(newItem)

        const newInstance = new ProductSearch(this.nextIndex)
        this.productSearchInstances.push(newInstance)

        this.nextIndex++
        this.updateAddButtonState()
    }

    // Удаление элемента поставки
    removeSupplyItem(index) {
        if (document.querySelectorAll('.supply__item').length <= 1) {
            alert('Должен остаться хотя бы один товар в поставке')
            return
        }

        const item = document.getElementById(`productSearch_${index}`)?.closest('.supply__item')
        if (item) {
            item.remove()
            this.productSearchInstances = this.productSearchInstances.filter(
                instance => instance.index !== index
            )

            document.dispatchEvent(new CustomEvent('productSelected', {
                detail: {sourceIndex: -1}
            }))

            this.updateAddButtonState()
        }
    }

    // Получить количество выбранных товаров
    getSelectedProductsCount() {
        return Array.from(document.querySelectorAll('[id^="productId_"]'))
            .filter(input => input.value).length
    }

    // Собрать данные из формы
    collectFormData() {
        const supplyData = {}

        document.querySelectorAll('.supply__item').forEach((item) => {
            const hiddenInput = item.querySelector('[id^="productId_"]')
            const quantityInput = item.querySelector('.product-quantity')

            if (hiddenInput?.value && quantityInput?.value) {
                supplyData[hiddenInput.value] = parseInt(quantityInput.value, 10)
            }
        })

        return supplyData
    }

    // Валидация формы
    validateForm(supplyData) {
        if (Object.keys(supplyData).length === 0) {
            alert('Добавьте хотя бы один товар с количеством')
            return false
        }

        const hasEmptyFields = Array.from(document.querySelectorAll('.supply__item')).some(item => {
            const hiddenInput = item.querySelector('[id^="productId_"]')
            const quantityInput = item.querySelector('.product-quantity')
            return !hiddenInput?.value || !quantityInput?.value
        })

        if (hasEmptyFields) {
            alert('Заполните все поля: товар и количество')
            return false
        }

        return true
    }

    // Обработка отправки формы
    handleSubmit(e) {
        e.preventDefault()

        const supplyData = this.collectFormData()

        if (!this.validateForm(supplyData)) return

        console.log('Отправляемые данные:', supplyData)
        this.submitToBackend(supplyData)
    }

    // Отправка данных на бэкенд
    submitToBackend(supplyData) {
        const actionUrl = this.form.getAttribute('action')
        const csrfToken = this.form.querySelector('input[name="_csrf"]')?.value

        const formData = new URLSearchParams()
        if (csrfToken) formData.append('_csrf', csrfToken)

        for (const [productId, quantity] of Object.entries(supplyData)) {
            formData.append(`productsId[${productId}]`, quantity)
        }

        fetch(actionUrl, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: formData.toString()
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "/warehouse-manager/dashboard?table=supplies"
                } else {
                    return response.text().then(text => {
                        throw new Error(text || 'Ошибка при создании поставки')
                    })
                }
            })
            .catch(error => console.error('Ошибка:', error))
    }

    // Обновить состояние кнопки добавления
    updateAddButtonState() {
        if (!this.addButton) return

        const isDisabled = this.getSelectedProductsCount() >= this.totalProducts
        this.addButton.disabled = isDisabled
        this.addButton.style.opacity = isDisabled ? '0.5' : '1'
        this.addButton.style.cursor = isDisabled ? 'not-allowed' : 'pointer'
    }

    bindEvents() {
        this.addButton?.addEventListener('click', () => this.addNewSupplyItem())
        this.form?.addEventListener('submit', (e) => this.handleSubmit(e))
        document.addEventListener('productSelected', () => this.updateAddButtonState())
    }
}

let supplyManager
document.addEventListener('DOMContentLoaded', () => {
    supplyManager = new SupplyManager()
})
