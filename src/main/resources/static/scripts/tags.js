class Tags {
  selectors = {
    searchInput: `storeSearch`,
    hiddenInput: `storeId`,
    dropdown: `storeDropdown`,
    options: '.custom-select-option',
    emptyMessage: '.empty-message',
  }

  stateClasses = {
    show: `show`,
    hidden: `hidden`,
  }

  constructor() {
    this.searchInput = document.getElementById(this.selectors.searchInput)
    this.hiddenInput = document.getElementById(this.selectors.hiddenInput)
    this.dropdown = document.getElementById(this.selectors.dropdown)
    this.emptyMessage = document.querySelector(this.selectors.emptyMessage)

    // Получаем только опции с data-value, исключая empty-message
    this.options = Array.from(this.dropdown.querySelectorAll(this.selectors.options))
      .filter(option => !option.classList.contains('empty-message'))

    this.bindEvents()
  }

  showNoResultsMessage() {
    this.emptyMessage.classList.add(this.stateClasses.show)
  }

  removeNoResultsMessage() {
    this.emptyMessage.classList.remove(this.stateClasses.show)
  }

  filterOptions(searchTerm) {
    let hasVisibleOptions = false

    this.options.forEach(option => {
      const text = option.textContent.toLowerCase().trim()

      if (text.includes(searchTerm)) {
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
    }
  }

  onDocumentClick(e) {
    if (!this.searchInput.contains(e.target) && !this.dropdown.contains(e.target)) {
      this.dropdown.classList.remove(this.stateClasses.show)
    }
  }

  bindEvents() {
    this.searchInput.addEventListener('focus', () => this.onFocusSearchInput())
    this.searchInput.addEventListener('input', () => this.onInputSearchInput())
    this.options.forEach((option) =>
      option.addEventListener('click', (e) => this.onOptionClick(e))
    )

    document.addEventListener('click', e => this.onDocumentClick(e))
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const storeSearch = document.getElementById('storeSearch')
  if (storeSearch) {
    new Tags()
  }
})
