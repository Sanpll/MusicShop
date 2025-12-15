 // Класс для поиска и выбора товара в поставке
 // Расширяет CustomSelect добавлением логики исключения уже выбранных товаров
class ProductSearch extends CustomSelect {
  constructor(index) {
    super({
      searchInputId: `productSearch_${index}`,
      hiddenInputId: `productId_${index}`,
      dropdownId: `productDropdown_${index}`,
    })

    this.index = index
  }

 // Получить список уже выбранных товаров (кроме текущего)
  getSelectedProducts() {
    const selectedProducts = []
    const allSearchInputs = document.querySelectorAll('.product-search')

    allSearchInputs.forEach((input) => {
      if (input.id === this.searchInput.id) return

      const match = input.id.match(/productSearch_(\d+)/)
      if (!match) return

      const realIndex = parseInt(match[1], 10)
      const hiddenInput = document.getElementById(`productId_${realIndex}`)

      if (hiddenInput?.value) {
        const dropdown = document.getElementById(`productDropdown_${realIndex}`)
        const selectedOption = dropdown?.querySelector(`[data-value="${hiddenInput.value}"]`)

        if (selectedOption) {
          const productName = selectedOption.getAttribute('data-name') || selectedOption.textContent.trim()
          selectedProducts.push(productName.toLowerCase())
        }
      }
    })

    return selectedProducts
  }

  // Переопределяем метод для добавления проверки уже выбранных товаров
  shouldShowOption(option, text, searchTerm) {
    const matchesSearch = text.includes(searchTerm)
    const productName = (option.getAttribute('data-name') || text).toLowerCase()
    const isAlreadySelected = this.getSelectedProducts().includes(productName)

    return matchesSearch && !isAlreadySelected
  }

  // Callback после выбора значения - уведомляем другие поля
  onSelectValue(value, text) {
    this.notifyOtherSearches()
  }

  onInput() {
    super.onInput()

    if (this.searchInput.value === '' && this.hiddenInput.value !== '') {
      this.hiddenInput.value = ''
      this.notifyOtherSearches()
    }
  }

  // Уведомить другие поля о выборе товара
  notifyOtherSearches() {
    document.dispatchEvent(new CustomEvent('productSelected', {
      detail: { sourceIndex: this.index }
    }))
  }

  // Обработчик события выбора товара в другом поле
  onProductSelectedElsewhere() {
    if (this.dropdown.classList.contains(this.stateClasses.show)) {
      const searchTerm = this.searchInput.value.toLowerCase()
      this.filterOptions(searchTerm)
    }
  }

  bindEvents() {
    super.bindEvents()

    // Слушаем события выбора товара в других полях
    document.addEventListener('productSelected', (e) => {
      if (e.detail.sourceIndex !== this.index) {
        this.onProductSelectedElsewhere()
      }
    })
  }
}
