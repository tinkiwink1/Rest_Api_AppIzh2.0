$(async function () {
    await addUser()
})

const userForm = document.forms['form-createUser']

async function addUser() {
    userForm.addEventListener('submit', event => {
        event.preventDefault()
        const roleSelect = document.getElementById('rolesNewUser');
        let newUserRoles = []
        for (let i = 0; i < roleSelect.options.length; i++) {
            if (roleSelect.options[i].selected) {
                newUserRoles.push({
                    id : parseInt(roleSelect.options[i].value),
                    authority : "ROLE_" + roleSelect.options[i].text
                })
            }
        }
        console.log(JSON.stringify(userForm.username.value))
        console.log(JSON.stringify(userForm.password.value))
        console.log(JSON.stringify(userForm.email.value))
        console.log(JSON.stringify(newUserRoles))
        fetch('api/users', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({
                username: userForm.username.value,
                password: userForm.password.value,
                email: userForm.email.value,
                roles: newUserRoles
            })
        })
            .then(() => {
                userForm.reset()
                getAllUsers()
                $('#users-tab').click()
            }).catch(() => console.error('Error create: ' + err))
    })
}