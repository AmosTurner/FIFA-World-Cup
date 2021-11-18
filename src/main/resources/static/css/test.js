
/* Function to get confirm the users intention to delete a team */
function confirmSubmit() {
    const confirm = window.confirm("Are you sure you want to delete this team?");
    if (confirm) {
        return true;
    }
    else {
        return false;
    }
}