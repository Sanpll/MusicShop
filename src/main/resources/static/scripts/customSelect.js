 // Базовый класс для кастомного select с поиском
class CustomSelect {
  stateClasses = {
    show: 'show',
    hidden: 'hidden',
  }

  constructor(config) {
    const {
      searchInputId,
      hiddenInputId,
      dropdownId,
      optionSelector = '.custom-select-option',
      emptyMessageSelector = '.empty-message'
    } = config

    this.searchInput = document.getElementById(searchInputId)
    this.hiddenInput = document.getElementById(hiddenInputId)
    this.dropdown = document.getElementById(dropdownId)

    if (!this.searchInput || !this.hiddenInput || !this.dropdown) {
      console.error(`Elements not found for CustomSelect: ${searchInputId}`)
      return
    }

    this.emptyMessage = this.dropdown.querySelector(emptyMessageSelector)
    this.options = Array.from(this.dropdown.querySelectorAll(optionSelector))
      .filter(option => !option.classList.contains('empty-message'))

    this.init()
  }

  init() {
    this.hideEmptyMessage()
    this.bindEvents()
  }

  showEmptyMessage() {
    this.emptyMessage?.classList.add(this.stateClasses.show)
  }

  hideEmptyMessage() {
    this.emptyMessage?.classList.remove(this.stateClasses.show)
  }

   // Фильтрация опций по поисковому запросу
  filterOptions(searchTerm) {
    let hasVisibleOptions = false

    this.options.forEach(option => {
      const text = option.textContent.toLowerCase().trim()
      const isVisible = this.shouldShowOption(option, text, searchTerm)

      option.style.display = isVisible ? 'block' : 'none'
      if (isVisible) hasVisibleOptions = true
    })

    searchTerm && !hasVisibleOptions ? this.showEmptyMessage() : this.hideEmptyMessage()
  }

  // Определяет, должна ли опция быть видимой
  shouldShowOption(option, text, searchTerm) {
    return text.includes(searchTerm)
  }

  onFocus() {
    this.dropdown.classList.add(this.stateClasses.show)
    this.filterOptions('')
  }

  onInput() {
    const searchTerm = this.searchInput.value.toLowerCase()
    this.filterOptions(searchTerm)
  }

  onOptionClick(e) {
    const value = e.currentTarget.getAttribute('data-value')
    const text = e.currentTarget.textContent.trim()

    if (value) {
      this.hiddenInput.value = value
      this.searchInput.value = text
      this.closeDropdown()
      this.onSelectValue(value, text)
    }
  }

  onSelectValue(value, text) {
    // Для будущей перезаписи
  }

  onDocumentClick(e) {
    if (!this.searchInput.contains(e.target) && !this.dropdown.contains(e.target)) {
      this.closeDropdown()
    }
  }

  closeDropdown() {
    this.dropdown.classList.remove(this.stateClasses.show)
  }

  bindEvents() {
    this.searchInput.addEventListener('focus', () => this.onFocus())
    this.searchInput.addEventListener('input', () => this.onInput())
    this.options.forEach(option =>
      option.addEventListener('click', (e) => this.onOptionClick(e))
    )
    document.addEventListener('click', (e) => this.onDocumentClick(e))
  }
}
