



export const saveUserInfo = async(userInfo) => {
    const userDb = []

    const {email, password} = userInfo;

    try {
        userDb.push(
            {
                "email" : email,
                "password" : password
            }
        )

        console.log(userDb)
    }
    catch (err) {
        console.error(err)
    }

}

