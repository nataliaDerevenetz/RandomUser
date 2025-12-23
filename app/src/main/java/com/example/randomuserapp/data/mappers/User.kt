package com.example.randomuserapp.data.mappers

import com.example.randomuserapp.data.local.models.UserEntity
import com.example.randomuserapp.data.remote.models.UserResponse
import com.example.randomuserapp.domain.models.Coordinates
import com.example.randomuserapp.domain.models.Dob
import com.example.randomuserapp.domain.models.Location
import com.example.randomuserapp.domain.models.Name
import com.example.randomuserapp.domain.models.Street
import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.utils.toDateFormatter
import com.example.randomuserapp.utils.toDateFromServerFormatted
import com.example.randomuserapp.utils.toFormattedString

fun User.toUserEntity(): UserEntity {
    return UserEntity(id = id, gender = gender,
        street_number = location.street.number, street_name = location.street.name,city = location.city,
        state = location.state, country = location.country, postcode = location.postcode,
        coordinates_latitude = location.coordinates.latitude, coordinates_longitude = location.coordinates.longitude,
        email = email, username = username,dob = dob.date.toFormattedString(),age = dob.age,
        phone = phone,cell = cell, picture = picture,nat =nat, title = name.title, first = name.first,
        last = name.last)
}

fun UserResponse.toUser(): User {
    val user = results.first()
    return User(
        id = user.login.uuid,
        name = Name(title = user.name.title, first = user.name.first, last = user.name.last),
        gender = user.gender,
        location = Location(city = user.location.city, street = Street(name = user.location.street.name, number = user.location.street.number), country = user.location.country,
            postcode = user.location.postcode, state = user.location.state, coordinates = Coordinates(longitude = user.location.coordinates.longitude, latitude = user.location.coordinates.latitude)),
        email = user.email,
        username = user.login.username,
        dob = Dob(date=user.dob.date.toDateFromServerFormatted(), age = user.dob.age),
        phone = user.phone,
        cell = user.cell,
        picture = user.picture.large,
        nat = user.nat
        )

}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = Name(title = title, first = first, last = last),
        gender = gender,
        location = Location(city = city, street = Street(name = street_name, number = street_number), country = country,
            postcode= postcode, state = state, coordinates = Coordinates(longitude = coordinates_longitude, latitude = coordinates_latitude)),
        email = email,
        username = username,
        dob = Dob(date = dob.toDateFormatter(), age = age),
        phone = phone,
        cell = cell,
        picture = picture,
        nat = nat
    )
}