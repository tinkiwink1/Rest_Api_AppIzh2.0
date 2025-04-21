$(async function () {
    await getAuthUser()
})

async function getAuthUser() {
    fetch('/api/user')
        .then(response => response.json())
        .then(data => {
            $('#authUsername').text(data.email)
            let roles = data.roles.map(role => ' ' + role.authority.substring(5))
            $('#authUsernameFields').append(roles)
            let user = $(`
                <tr>
                    <td>${data.id}</td>
                    <td>${data.username}</td>
                    <td>${data.password}</td>
                    <td>${data.email}</td>
                    <td>
                        ${data.roles.map(role => role.authority.substring(5) + ' ')}
                    </td>
                </tr>
            `)
            $('#user-info').append(user)
        })
        .catch(err => console.log(err))
}