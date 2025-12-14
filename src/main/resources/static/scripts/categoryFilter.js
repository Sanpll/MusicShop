class CategoryFilter {
  constructor() {
    this.form = document.querySelector('.home__controls .form')
    this.checkboxes = document.querySelectorAll('input[name="categoryIds"]')

    if (!this.form) {
      console.error('Form not found')
      return
    }

    this.bindEvents()
    this.loadSelectedCategories()
  }

  // Загрузить выбранные категории из URL при загрузке страницы
  loadSelectedCategories() {
    const urlParams = new URLSearchParams(window.location.search)
    const categories = urlParams.get('categories')

    if (categories) {
      const categoryIds = categories.split('-')

      this.checkboxes.forEach(checkbox => {
        if (categoryIds.includes(checkbox.value)) {
          checkbox.checked = true
        }
      })
    }
  }

  // Собрать выбранные категории
  getSelectedCategories() {
    const selected = []

    this.checkboxes.forEach(checkbox => {
      if (checkbox.checked) {
        selected.push(checkbox.value)
      }
    })

    return selected
  }

  // Обработка отправки формы
  onSubmit(e) {
    e.preventDefault()

    const selectedCategories = this.getSelectedCategories()

    // Формируем URL
    const baseUrl = window.location.pathname
    let url = baseUrl

    if (selectedCategories.length > 0) {
      // Формируем строку категорий через дефис
      const categoriesString = selectedCategories.join('-')
      url = `${baseUrl}?categories=${categoriesString}`
    }

    // Перенаправляем на URL с параметрами
    window.location.href = url
  }

  bindEvents() {
    this.form.addEventListener('submit', (e) => this.onSubmit(e))
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const categoryFilterForm = document.querySelector('.home__controls .form')
  if (categoryFilterForm) {
    new CategoryFilter()
  }
})
