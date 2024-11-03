// Selecting form and input elements
const form = document.querySelector("form");
const passwordInput = document.getElementById("password");
const passToggleBtn = document.getElementById("pass-toggle-btn");

// Function to display error messages
const showError = (field, errorText) => {
  field.classList.add("error");
  const errorElement = document.createElement("small");
  errorElement.classList.add("error-text");
  errorElement.innerText = errorText;
  field.closest(".form-input").appendChild(errorElement);
};

// Function to remove error messages
const clearError = (field) => {
  field.classList.remove("error");
  const errorText = field.closest(".form-input").querySelector(".error-text");
  if (errorText) {
    errorText.remove();
  }
};

// Function to validate email format
const validateEmail = (email) => {
  const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
  return emailPattern.test(email);
};

// Function to handle form submission
const handleFormData = (e) => {
  e.preventDefault();

  // Retrieving input elements
  const usernameInput = document.getElementById("username");
  const emailInput = document.getElementById("email");
  const policeNumberInput = document.getElementById("policeNumber");
  const rankInput = document.getElementById("rank");

  // Getting trimmed values from input fields
  const username = usernameInput.value.trim();
  const email = emailInput.value.trim();
  const policeNumber = policeNumberInput.value.trim();
  const rank = rankInput.value;

  // Clearing previous error messages
  document.querySelectorAll(".form-input .error").forEach(clearError);

  // Performing validation checks
  if (username === "") {
    showError(usernameInput, "*Enter your username");
  }
  if (email === "") {
    showError(emailInput, "*Enter your email");
  } else if (!validateEmail(email)) {
    showError(emailInput, "*Enter a valid email address");
  }
  if (policeNumber === "") {
    showError(policeNumberInput, "*Enter your police number");
  }
  if (rank === "") {
    showError(rankInput, "*Select your rank");
  }

  // Checking for any remaining errors before form submission
  const errorInputs = document.querySelectorAll(".form-input .error");
  if (errorInputs.length === 0) {
    // Submitting the form if there are no errors
    form.submit();
  }
};

// Toggling password visibility
passToggleBtn.addEventListener("click", () => {
  passToggleBtn.classList.toggle("fa-eye");
  passToggleBtn.classList.toggle("fa-eye-slash");
  passwordInput.type =
      passwordInput.type === "password" ? "text" : "password";
});

// Handling form submission event
form.addEventListener("submit", handleFormData);



// Handling form submission event
form.addEventListener("submit", (e) => {
  e.preventDefault();

  // Retrieve input elements
  const usernameInput = document.getElementById("username");
  const emailInput = document.getElementById("email");
  const policeNumberInput = document.getElementById("policeNumber");
  const rankInput = document.getElementById("rank");
  const passwordInput = document.getElementById("password");

  // Validation functions
  const showError = (field, errorText) => {
    field.classList.add("error");
    const errorElement = document.createElement("small");
    errorElement.classList.add("error-text");
    errorElement.innerText = errorText;
    field.closest(".form-input").appendChild(errorElement);
  };

  const clearError = (field) => {
    field.classList.remove("error");
    const errorText = field.closest(".form-input").querySelector(".error-text");
    if (errorText) {
      errorText.remove();
    }
  };

  const validateEmail = (email) => {
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    return emailPattern.test(email);
  };

  // Clear previous error messages
  document.querySelectorAll(".form-input .error").forEach(clearError);

  // Perform validation checks
  if (usernameInput.value.trim() === "") {
    showError(usernameInput, "*Enter your username");
  }
  if (emailInput.value.trim() === "") {
    showError(emailInput, "*Enter your email");
  } else if (!validateEmail(emailInput.value.trim())) {
    showError(emailInput, "*Enter a valid email address");
  }
  if (policeNumberInput.value.trim().length !== 5) {
    showError(policeNumberInput, "*Police Number must be 5 characters long");
  }
  if (rankInput.value.trim() === "") {
    showError(rankInput, "*Select your rank");
  }
  if (passwordInput.value.length < 6) {
    showError(passwordInput, "*Password must be at least 6 characters long");
  }

  // Check for remaining errors before form submission
  const errorInputs = document.querySelectorAll(".form-input .error");
  if (errorInputs.length === 0) {
    // Submit the form if there are no errors
    fetch(form.action, {
      method: form.method,
      body: new FormData(form)
    })
        .then(response => {
          // Assuming the response confirms successful user creation
          // Display success message
          const successMessage = document.querySelector(".success-message");
          successMessage.style.display = "block";

          // Optional: Reset form fields after successful submission
          form.reset();
        })
        .catch(error => console.error('Error:', error));
  }
});
