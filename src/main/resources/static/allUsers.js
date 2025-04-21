const tableContent = $("#table-content");
$(async function () {
    await getAllUsers()
})

async function getAllUsers() {
    tableContent.empty()
    fetch('/api/users', {
        method: 'GET',
        headers : {
            'Content-Type' : 'application/json'
        },
    })
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableWithUsers = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => ' ' + role.authority.substring(5))}</td>
                    <td>
                        <button class="btn btn-info" data-bs-toggle="modal" 
                        data-bs-target="#editModal" onclick="openEditModal(${user.id})">edit</button>
                    </td>
                    <td>
                        <button class="btn btn-danger" data-toggle="modal" 
                        data-bs-target="#deleteModal" onclick="openDeleteModal(${user.id})">delete</button>
                    </td>
                </tr>)`
            tableContent.append(tableWithUsers)
            })
        }).catch(err => console.log(err))
}