module Encoders
    exposing
        ( building
        , company
        , contactInformation
        , contactPerson
        , employedPerson
        , energyRequirements
        , location
        , logEntry
        , newLocation
        , newNotification
        , newWorkEntry
        , notification
        , notificationAction
        , notificationStatus
        , occupation
        , person
        , ship
        , shipArea
        , signup
        , timeOfDay
        , user
        , workEntry
        , workday
        , worker
        , workweek
        )

{-| Encoders module.
@docs building, company, contactInformation, contactPerson, employedPerson, energyRequirements, location, logEntry, newLocation, newNotification, newWorkEntry, notification, notificationAction, notificationStatus, occupation, person, ship, shipArea, signup, timeOfDay, user, workEntry, workday, worker, workweek
-}

import Date
import Domain exposing (..)
import Json.Encode exposing (..)


date : Date.Date -> Value
date o =
    string (toString o)


maybe : (a -> Value) -> Maybe a -> Value
maybe enc o =
    case o of
        Nothing ->
            null

        Just x ->
            enc x


{-| -}
building : Building -> Value
building o =
    object
        [ ( "id", int o.id )
        , ( "code", string o.code )
        , ( "description", string o.description )
        ]


{-| -}
company : Company -> Value
company o =
    object
        [ ( "id", int o.id )
        , ( "name", string o.name )
        ]


{-| -}
contactInformation : ContactInformation -> Value
contactInformation o =
    object
        [ ( "email", string o.email )
        , ( "phoneNumber", string o.phoneNumber )
        ]


{-| -}
contactPerson : ContactPerson -> Value
contactPerson o =
    object
        [ ( "employedPerson", employedPerson o.employedPerson )
        , ( "contactInformation", contactInformation o.contactInformation )
        ]


{-| -}
employedPerson : EmployedPerson -> Value
employedPerson o =
    object
        [ ( "person", person o.person )
        , ( "company", company o.company )
        ]


{-| -}
energyRequirements : EnergyRequirements -> Value
energyRequirements o =
    object
        [ ( "oxyacetylene", bool o.oxyacetylene )
        , ( "compositeGas", bool o.compositeGas )
        , ( "argon", bool o.argon )
        , ( "compressedAir", bool o.compressedAir )
        , ( "hotWorks", bool o.hotWorks )
        ]


{-| -}
location : Location -> Value
location o =
    object
        [ ( "building", building o.building )
        , ( "ship", (maybe ship) o.ship )
        , ( "shipArea", (maybe shipArea) o.shipArea )
        ]


{-| -}
logEntry : LogEntry -> Value
logEntry o =
    object
        [ ( "id", int o.id )
        , ( "action", notificationAction o.action )
        , ( "createdAt", date o.createdAt )
        , ( "createdBy", user o.createdBy )
        ]


{-| -}
newLocation : NewLocation -> Value
newLocation o =
    object
        [ ( "building", int o.building )
        , ( "ship", (maybe int) o.ship )
        , ( "shipArea", (maybe int) o.shipArea )
        ]


{-| -}
newNotification : NewNotification -> Value
newNotification o =
    object
        [ ( "status", notificationStatus o.status )
        , ( "yardContact", int o.yardContact )
        , ( "siteForeman", int o.siteForeman )
        , ( "additionalInformation", string o.additionalInformation )
        , ( "workWeek", workweek o.workWeek )
        , ( "workEntries", list (List.map newWorkEntry o.workEntries) )
        ]


{-| -}
newWorkEntry : NewWorkEntry -> Value
newWorkEntry o =
    object
        [ ( "worker", int o.worker )
        , ( "location", newLocation o.location )
        , ( "occupation", occupation o.occupation )
        , ( "energyRequirements", energyRequirements o.energyRequirements )
        ]


{-| -}
notification : Notification -> Value
notification o =
    object
        [ ( "id", int o.id )
        , ( "status", notificationStatus o.status )
        , ( "yardContact", contactPerson o.yardContact )
        , ( "siteForeman", contactPerson o.siteForeman )
        , ( "additionalInformation", string o.additionalInformation )
        , ( "workWeek", workweek o.workWeek )
        , ( "workEntries", list (List.map workEntry o.workEntries) )
        , ( "log", list (List.map logEntry o.log) )
        ]


{-| -}
notificationAction : NotificationAction -> Value
notificationAction o =
    string (toString o)


{-| -}
notificationStatus : NotificationStatus -> Value
notificationStatus o =
    string (toString o)


{-| -}
occupation : Occupation -> Value
occupation o =
    string (toString o)


{-| -}
person : Person -> Value
person o =
    object
        [ ( "id", int o.id )
        , ( "firstName", string o.firstName )
        , ( "lastName", string o.lastName )
        ]


{-| -}
ship : Ship -> Value
ship o =
    object
        [ ( "id", int o.id )
        , ( "code", string o.code )
        , ( "description", string o.description )
        ]


{-| -}
shipArea : ShipArea -> Value
shipArea o =
    object
        [ ( "id", int o.id )
        , ( "code", string o.code )
        , ( "description", string o.description )
        ]


{-| -}
signup : Signup -> Value
signup o =
    object
        [ ( "firstName", string o.firstName )
        , ( "lastName", string o.lastName )
        , ( "email", string o.email )
        , ( "phoneNumber", string o.phoneNumber )
        , ( "company", company o.company )
        , ( "password", string o.password )
        ]


{-| -}
timeOfDay : TimeOfDay -> Value
timeOfDay o =
    object
        [ ( "hour", int o.hour )
        , ( "minute", int o.minute )
        ]


{-| -}
user : User -> Value
user o =
    object
        [ ( "contactPerson", contactPerson o.contactPerson )
        , ( "confirmed", bool o.confirmed )
        , ( "token", string o.token )
        , ( "password", string o.password )
        ]


{-| -}
workEntry : WorkEntry -> Value
workEntry o =
    object
        [ ( "id", int o.id )
        , ( "worker", worker o.worker )
        , ( "location", location o.location )
        , ( "occupation", occupation o.occupation )
        , ( "energyRequirements", energyRequirements o.energyRequirements )
        ]


{-| -}
workday : Workday -> Value
workday o =
    object
        [ ( "startTime", timeOfDay o.startTime )
        , ( "endTime", timeOfDay o.endTime )
        ]


{-| -}
worker : Worker -> Value
worker o =
    object
        [ ( "employedPerson", employedPerson o.employedPerson )
        , ( "keyCode", string o.keyCode )
        ]


{-| -}
workweek : Workweek -> Value
workweek o =
    object
        [ ( "weekNumber", int o.weekNumber )
        , ( "monday", (maybe workday) o.monday )
        , ( "tuesday", (maybe workday) o.tuesday )
        , ( "wednesday", (maybe workday) o.wednesday )
        , ( "thursday", (maybe workday) o.thursday )
        , ( "friday", (maybe workday) o.friday )
        , ( "saturday", (maybe workday) o.saturday )
        , ( "sunday", (maybe workday) o.sunday )
        ]
