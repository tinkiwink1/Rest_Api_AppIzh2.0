let editModalForm = document.forms['editModalForm']
let deleteModalForm = document.forms['deleteModalForm']


$(async function () {
    await editUser()
    await deleteUser()
})

async function openEditModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#editModal'))
    await fillModalEditForm(editModalForm, modal, id)
}

async function fillModalEditForm(form, modal, id) {
    modal.show()
    let user = await getUser(id)
    form.id.value = user.id
    form.username.value = user.username
    form.email.value = user.email
    form.rolesName.value = user.roles[0].id
}

async function getUser(id) {
    let response = await fetch('api/users/' + id)
    return await response.json()
}

async function editUser() {
    editModalForm.addEventListener('submit', async (event) => {
        event.preventDefault()
        const roleEditSelect = editModalForm.elements.rolesName
        let editedUserRoles = []
        for (let i = 0; i < roleEditSelect.options.length; i++) {
            if (roleEditSelect.options[i].selected) {
                editedUserRoles.push({
                    id: parseInt(roleEditSelect.options[i].value),
                    authority: 'ROLE_' + roleEditSelect.options[i].text
                })
            }
        }
        console.log("password " + editModalForm.id.value)
        fetch('/api/users', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: parseInt(editModalForm.id.value),
                username: editModalForm.username.value,
                email: editModalForm.email.value,
                password: editModalForm.password.value,
                roles: editedUserRoles
            })
        }).then(() => {
            $('#editModalCloseBtn').click()
            getAllUsers()
        })
    })
}

async function fillModalDeleteForm(form, modal, id) {
    modal.show()
    let user = await getUser(id)
    form.idDel.value = user.id
    form.usernameDel.value = user.username
    form.emailDel.value = user.email
    form.rolesInDelete.value = user.roles.map(role => role.authority.substring(5) + ' ')
}
async function openDeleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'))
    await fillModalDeleteForm(deleteModalForm, modal, id)

}
async function deleteUser() {
    deleteModalForm.addEventListener('submit' ,event => {
        event.preventDefault()
        fetch('api/users/' + deleteModalForm.idDel.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#deleteModalCloseBtn').click()
            getAllUsers()
        }).catch(err => alert('Something to do wrong: ' + err))
    })

}
