// Main JavaScript for CHU El Jadida Management System

document.addEventListener("DOMContentLoaded", () => {
  // Initialize Bootstrap tooltips
  const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  tooltipTriggerList.forEach((tooltipTriggerEl) => {
    new bootstrap.Tooltip(tooltipTriggerEl)
  })

  // Initialize Bootstrap popovers
  const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
  popoverTriggerList.forEach((popoverTriggerEl) => {
    new bootstrap.Popover(popoverTriggerEl)
  })

  // Auto-close alerts after 5 seconds
  setTimeout(() => {
    const alerts = document.querySelectorAll(".alert:not(.alert-permanent)")
    alerts.forEach((alert) => {
      const bsAlert = new bootstrap.Alert(alert)
      bsAlert.close()
    })
  }, 5000)

  // Confirmation for delete actions
  const deleteButtons = document.querySelectorAll("[data-confirm]")
  deleteButtons.forEach((button) => {
    button.addEventListener("click", function (e) {
      const message = this.getAttribute("data-confirm") || "Êtes-vous sûr de vouloir effectuer cette action ?"
      if (!confirm(message)) {
        e.preventDefault()
      }
    })
  })

  // Form validation
  const forms = document.querySelectorAll(".needs-validation")
  Array.from(forms).forEach((form) => {
    form.addEventListener(
      "submit",
      (event) => {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add("was-validated")
      },
      false,
    )
  })

  // Personnel dropdown toggle
  const personnelDropdownToggle = document.querySelector(".personnel-dropdown-toggle")
  if (personnelDropdownToggle) {
    personnelDropdownToggle.addEventListener("click", (e) => {
      e.preventDefault()
      const dropdown = document.querySelector(".personnel-dropdown")
      dropdown.classList.toggle("show")
    })

    // Close dropdown when clicking outside
    document.addEventListener("click", (e) => {
      const dropdown = document.querySelector(".personnel-dropdown")
      const toggle = document.querySelector(".personnel-dropdown-toggle")
      if (dropdown && !dropdown.contains(e.target) && !toggle.contains(e.target)) {
        dropdown.classList.remove("show")
      }
    })
  }

  // Search functionality for tables
  const searchInputs = document.querySelectorAll(".search-input")
  searchInputs.forEach((input) => {
    input.addEventListener("keyup", function () {
      const searchTerm = this.value.toLowerCase()
      const tableId = this.getAttribute("data-table")
      const table = document.getElementById(tableId) || this.closest(".card").querySelector("table")

      if (table) {
        const rows = table.querySelectorAll("tbody tr")

        rows.forEach((row) => {
          const text = row.textContent.toLowerCase()
          if (text.includes(searchTerm)) {
            row.style.display = ""
          } else {
            row.style.display = "none"
          }
        })
      }
    })
  })

  // Filter functionality for tables
  const filterSelects = document.querySelectorAll(".filter-select")
  filterSelects.forEach((select) => {
    select.addEventListener("change", function () {
      const filterValue = this.value.toLowerCase()
      const filterColumn = this.getAttribute("data-column")
      const tableId = this.getAttribute("data-table")
      const table = document.getElementById(tableId) || this.closest(".card").querySelector("table")

      if (table) {
        const rows = table.querySelectorAll("tbody tr")

        rows.forEach((row) => {
          if (!filterValue) {
            row.style.display = ""
            return
          }

          const cell = row.querySelector(`td[data-column="${filterColumn}"]`)
          if (cell) {
            const cellValue = cell.textContent.toLowerCase()
            if (cellValue.includes(filterValue)) {
              row.style.display = ""
            } else {
              row.style.display = "none"
            }
          }
        })
      }
    })
  })

  // Toggle sidebar on mobile
  const sidebarToggle = document.querySelector(".sidebar-toggle")
  if (sidebarToggle) {
    sidebarToggle.addEventListener("click", () => {
      document.body.classList.toggle("sidebar-open")
    })
  }

  // Initialize date pickers
  const datePickers = document.querySelectorAll(".datepicker")
  if (datePickers.length > 0 && typeof flatpickr !== "undefined") {
    datePickers.forEach((picker) => {
      flatpickr(picker, {
        dateFormat: "d/m/Y",
      })
    })
  }

  // Initialize select2 for enhanced select boxes
  const select2Elements = document.querySelectorAll(".select2")
  if (select2Elements.length > 0 && typeof $.fn.select2 !== "undefined") {
    $(select2Elements).select2({
      width: "100%",
    })
  }

  // Responsive tables
  const responsiveTables = document.querySelectorAll(".table-responsive-stack")
  responsiveTables.forEach((table) => {
    const thArray = []
    table.querySelectorAll("th").forEach((th) => {
      thArray.push(th.textContent)
    })

    table.querySelectorAll("tbody tr").forEach((tr) => {
      tr.querySelectorAll("td").forEach((td, index) => {
        td.setAttribute("data-label", thArray[index])
      })
    })
  })

  // Check if Bootstrap is properly loaded
  if (typeof bootstrap === "undefined") {
    console.warn("Bootstrap is not properly loaded. Ensure Bootstrap CSS and JS are included.")
  }
  if (typeof flatpickr === "undefined") {
    console.warn("flatpickr is not properly loaded. Ensure flatpickr CSS and JS are included.")
  }
  if (typeof $ === "undefined") {
    console.warn("jQuery is not properly loaded. Ensure jQuery is included.")
  }
})

