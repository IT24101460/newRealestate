// API endpoints
const API_BASE_URL = 'http://localhost:8080/api';
const USERS_ENDPOINT = `${API_BASE_URL}/users`;
const PROPERTIES_ENDPOINT = `${API_BASE_URL}/properties`;

// Load users when the page loads
document.addEventListener('DOMContentLoaded', loadUsers);

// Function to load all users
async function loadUsers() {
    try {
        const response = await fetch(USERS_ENDPOINT);
        const users = await response.json();
        displayUsers(users);
    } catch (error) {
        console.error('Error loading users:', error);
        alert('Error loading users. Please try again.');
    }
}

// Function to display users in the table
function displayUsers(users) {
    const tableBody = document.getElementById('usersTableBody');
    tableBody.innerHTML = '';

    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <button class="btn btn-sm btn-info btn-action" onclick="viewUser('${user.id}')">View</button>
                <button class="btn btn-sm btn-danger btn-action" onclick="deleteUser('${user.id}')">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

// Function to add a new user
async function addUser() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    const userData = {
        username,
        email,
        password,
        role
    };

    try {
        const response = await fetch(USERS_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            // Close modal and refresh user list
            const modal = bootstrap.Modal.getInstance(document.getElementById('addUserModal'));
            modal.hide();
            document.getElementById('addUserForm').reset();
            loadUsers();
        } else {
            throw new Error('Failed to add user');
        }
    } catch (error) {
        console.error('Error adding user:', error);
        alert('Error adding user. Please try again.');
    }
}

// Function to view user details
async function viewUser(userId) {
    try {
        const response = await fetch(`${USERS_ENDPOINT}/${userId}`);
        const user = await response.json();
        
        const userDetails = document.getElementById('userDetails');
        userDetails.innerHTML = `
            <p><strong>ID:</strong> ${user.id}</p>
            <p><strong>Username:</strong> ${user.username}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Role:</strong> ${user.role}</p>
        `;

        const modal = new bootstrap.Modal(document.getElementById('viewUserModal'));
        modal.show();
    } catch (error) {
        console.error('Error loading user details:', error);
        alert('Error loading user details. Please try again.');
    }
}

// Function to delete a user
async function deleteUser(userId) {
    if (!confirm('Are you sure you want to delete this user?')) {
        return;
    }

    try {
        const response = await fetch(`${USERS_ENDPOINT}/${userId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadUsers();
        } else {
            throw new Error('Failed to delete user');
        }
    } catch (error) {
        console.error('Error deleting user:', error);
        alert('Error deleting user. Please try again.');
    }
}

// Function to edit a user
async function editUser(userId) {
    try {
        const response = await fetch(`${USERS_ENDPOINT}/${userId}`);
        const user = await response.json();
        
        // Populate the edit form
        document.getElementById('editUserId').value = user.id;
        document.getElementById('editUsername').value = user.username;
        document.getElementById('editEmail').value = user.email;
        document.getElementById('editRole').value = user.role;
        document.getElementById('editPassword').value = ''; // Clear password field
        
        // Show the modal
        const modal = new bootstrap.Modal(document.getElementById('editUserModal'));
        modal.show();
    } catch (error) {
        console.error('Error loading user details:', error);
        alert('Error loading user details. Please try again.');
    }
}

// Function to update a user
async function updateUser() {
    const userId = document.getElementById('editUserId').value;
    const username = document.getElementById('editUsername').value;
    const email = document.getElementById('editEmail').value;
    const password = document.getElementById('editPassword').value;
    const role = document.getElementById('editRole').value;

    const userData = {
        username,
        email,
        role
    };

    // Only include password if it's not empty
    if (password) {
        userData.password = password;
    }

    try {
        const response = await fetch(`${USERS_ENDPOINT}/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            // Close modal and refresh user list
            const modal = bootstrap.Modal.getInstance(document.getElementById('editUserModal'));
            modal.hide();
            document.getElementById('editUserForm').reset();
            loadUsers();
        } else {
            throw new Error('Failed to update user');
        }
    } catch (error) {
        console.error('Error updating user:', error);
        alert('Error updating user. Please try again.');
    }
}

// Function to add a new property
async function addProperty() {
    const propertyData = {
        title: document.getElementById('title').value,
        location: document.getElementById('location').value,
        price: parseFloat(document.getElementById('price').value),
        type: document.getElementById('type').value,
        description: document.getElementById('description').value,
        imageUrl: document.getElementById('imageUrl').value,
        ownerId: document.getElementById('ownerId').value,
        available: true
    };

    console.log('Sending property data:', propertyData); // Debug log

    try {
        const response = await fetch(PROPERTIES_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(propertyData)
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Property added successfully:', result); // Debug log
            // Close modal and refresh property list
            const modal = bootstrap.Modal.getInstance(document.getElementById('addPropertyModal'));
            modal.hide();
            document.getElementById('addPropertyForm').reset();
            loadProperties();
        } else {
            const error = await response.json().catch(() => ({ message: 'Failed to add property' }));
            console.error('Failed to add property:', error); // Debug log
            throw new Error(error.message || 'Failed to add property');
        }
    } catch (error) {
        console.error('Error adding property:', error);
        alert('Error adding property: ' + error.message);
    }
} 