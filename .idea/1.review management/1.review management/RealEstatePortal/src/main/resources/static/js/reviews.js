// Submit Review
document.getElementById('reviewForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const params = new URLSearchParams({
        propertyId: document.getElementById('propertyId').value,
        buyerId: document.getElementById('buyerId').value,
        rating: document.getElementById('rating').value,
        comment: document.getElementById('comment').value
    });
    try {
        const response = await fetch('/reviews?' + params.toString(), {
            method: 'POST'
        });
        if (response.ok) {
            alert('Review submitted successfully!');
            document.getElementById('reviewForm').reset();
            loadReviews(document.getElementById('propertyId').value);
        } else {
            alert('Failed to submit review. Status: ' + response.status);
            console.error('Response:', await response.text());
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred.');
    }
});

// Load Reviews for a Property
async function loadReviews(propertyId) {
    try {
        const response = await fetch(`/reviews/property/${propertyId}`);
        const reviews = await response.json();
        const tbody = document.getElementById('reviewsBody');
        tbody.innerHTML = '';
        if (reviews.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3" class="no-reviews">No reviews yet. Be the first to review!</td></tr>';
        } else {
            reviews.forEach(review => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td><span class="star-rating">${'★'.repeat(review.rating)}${'☆'.repeat(5 - review.rating)}</span></td>
                    <td>${review.comment}</td>
                    <td>${review.date}</td>
                `;
                tbody.appendChild(row);
            });
        }
    } catch (error) {
        console.error('Error loading reviews:', error);
        alert('Failed to load reviews.');
    }
}

// Load reviews on page load (example with propertyId P001)
window.onload = () => loadReviews('P001');
