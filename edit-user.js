// Get userId from URL
const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('id');

const form = document.getElementById('editUserForm');
const messageDiv = document.getElementById('message');

// Fetch user details and populate the form
fetch(`/users/${userId}`)
    .then(response => {
        if (!response.ok) throw new Error('User not found');
        return response.json();
    })
    .then(user => {
        document.getElementById('name').value = user.name;
        document.getElementById('email').value = user.email;
        document.getElementById('password').value = user.password;
    })
    .catch(error => {
        messageDiv.textContent = 'Error loading user details.';
        messageDiv.style.color = 'red';
    });

// Handle form submission
form.addEventListener('submit', function(event) {
    event.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch(`/users/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            name: name,
            email: email,
            password: password
        })
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to update user');
        return response.json();
    })
    .then(user => {
        messageDiv.textContent = 'User updated successfully!';
        messageDiv.style.color = 'green';
        setTimeout(() => {
            window.location.href = '/admin/dashboard.html';
        }, 1000);
    })
    .catch(error => {
        messageDiv.textContent = 'Error updating user.';
        messageDiv.style.color = 'red';
    });
}); 