 // Класс для выбора магазина/тега
class Tags extends CustomSelect {
  constructor() {
    super({
      searchInputId: 'storeSearch',
      hiddenInputId: 'storeId',
      dropdownId: 'storeDropdown',
    })
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const storeSearch = document.getElementById('storeSearch')
  if (storeSearch) {
    new Tags()
  }
})
