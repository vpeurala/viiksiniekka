module Decoders
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

{-| Decoders module.
@docs building, company, contactInformation, contactPerson, employedPerson, energyRequirements, location, logEntry, newLocation, newNotification, newWorkEntry, notification, notificationAction, notificationStatus, occupation, person, ship, shipArea, signup, timeOfDay, user, workEntry, workday, worker, workweek
-}

import Date
import Domain exposing (..)
import Json.Decode exposing (..)


date : Decoder Date.Date
date =
    string
        |> andThen
            (\s ->
                case (Date.fromString s) of
                    Ok result ->
                        succeed result

                    Err message ->
                        fail message
            )


{-| -}
building : Decoder Building
building =
    map3 Building (field "id" int) (field "code" string) (field "description" string)


{-| -}
company : Decoder Company
company =
    map2 Company (field "id" int) (field "name" string)


{-| -}
contactInformation : Decoder ContactInformation
contactInformation =
    map2 ContactInformation (field "email" string) (field "phoneNumber" string)


{-| -}
contactPerson : Decoder ContactPerson
contactPerson =
    map2 ContactPerson (field "employedPerson" employedPerson) (field "contactInformation" contactInformation)


{-| -}
employedPerson : Decoder EmployedPerson
employedPerson =
    map2 EmployedPerson (field "person" person) (field "company" company)


{-| -}
energyRequirements : Decoder EnergyRequirements
energyRequirements =
    map5 EnergyRequirements (field "oxyacetylene" bool) (field "compositeGas" bool) (field "argon" bool) (field "compressedAir" bool) (field "hotWorks" bool)


{-| -}
location : Decoder Location
location =
    map3 Location (field "building" building) (field "ship" (maybe ship)) (field "shipArea" (maybe shipArea))


{-| -}
logEntry : Decoder LogEntry
logEntry =
    map4 LogEntry (field "id" int) (field "action" notificationAction) (field "createdAt" date) (field "createdBy" user)


{-| -}
newLocation : Decoder NewLocation
newLocation =
    map3 NewLocation (field "building" int) (field "ship" (maybe int)) (field "shipArea" (maybe int))


{-| -}
newNotification : Decoder NewNotification
newNotification =
    map6 NewNotification (field "status" notificationStatus) (field "yardContact" int) (field "siteForeman" int) (field "additionalInformation" string) (field "workWeek" workweek) (field "workEntries" (list newWorkEntry))


{-| -}
newWorkEntry : Decoder NewWorkEntry
newWorkEntry =
    map4 NewWorkEntry (field "worker" int) (field "location" newLocation) (field "occupation" occupation) (field "energyRequirements" energyRequirements)


{-| -}
notification : Decoder Notification
notification =
    map8 Notification (field "id" int) (field "status" notificationStatus) (field "yardContact" contactPerson) (field "siteForeman" contactPerson) (field "additionalInformation" string) (field "workWeek" workweek) (field "workEntries" (list workEntry)) (field "log" (list logEntry))


{-| -}
notificationAction : Decoder NotificationAction
notificationAction =
    string
        |> andThen
            (\s ->
                case s of
                    "Create" ->
                        succeed Create

                    "Send" ->
                        succeed Send

                    "Approve" ->
                        succeed Approve

                    "Reject" ->
                        succeed Reject

                    "Edit" ->
                        succeed Edit

                    other ->
                        fail ("Expected NotificationAction, but was '" ++ other ++ "'.")
            )


{-| -}
notificationStatus : Decoder NotificationStatus
notificationStatus =
    string
        |> andThen
            (\s ->
                case s of
                    "Approved" ->
                        succeed Approved

                    "Draft" ->
                        succeed Draft

                    "Rejected" ->
                        succeed Rejected

                    "Waiting for yard contact" ->
                        succeed WaitingForYardContact

                    "Waiting for production manager" ->
                        succeed WaitingForProductionManager

                    other ->
                        fail ("Expected NotificationStatus, but was '" ++ other ++ "'.")
            )


{-| -}
occupation : Decoder Occupation
occupation =
    string
        |> andThen
            (\s ->
                case s of
                    "Fitter" ->
                        succeed Fitter

                    "Welder" ->
                        succeed Welder

                    "Pipe fitter" ->
                        succeed PipeFitter

                    "Project manager" ->
                        succeed ProjectManager

                    "Other" ->
                        succeed Other

                    other ->
                        fail ("Expected Occupation, but was '" ++ other ++ "'.")
            )


{-| -}
person : Decoder Person
person =
    map3 Person (field "id" int) (field "firstName" string) (field "lastName" string)


{-| -}
ship : Decoder Ship
ship =
    map3 Ship (field "id" int) (field "code" string) (field "description" string)


{-| -}
shipArea : Decoder ShipArea
shipArea =
    map3 ShipArea (field "id" int) (field "code" string) (field "description" string)


{-| -}
signup : Decoder Signup
signup =
    map6 Signup (field "firstName" string) (field "lastName" string) (field "email" string) (field "phoneNumber" string) (field "company" company) (field "password" string)


{-| -}
timeOfDay : Decoder TimeOfDay
timeOfDay =
    map2 TimeOfDay (field "hour" int) (field "minute" int)


{-| -}
user : Decoder User
user =
    map4 User (field "contactPerson" contactPerson) (field "confirmed" bool) (field "token" string) (field "password" string)


{-| -}
workEntry : Decoder WorkEntry
workEntry =
    map5 WorkEntry (field "id" int) (field "worker" worker) (field "location" location) (field "occupation" occupation) (field "energyRequirements" energyRequirements)


{-| -}
workday : Decoder Workday
workday =
    map2 Workday (field "startTime" timeOfDay) (field "endTime" timeOfDay)


{-| -}
worker : Decoder Worker
worker =
    map2 Worker (field "employedPerson" employedPerson) (field "keyCode" string)


{-| -}
workweek : Decoder Workweek
workweek =
    map8 Workweek (field "weekNumber" int) (field "monday" (maybe workday)) (field "tuesday" (maybe workday)) (field "wednesday" (maybe workday)) (field "thursday" (maybe workday)) (field "friday" (maybe workday)) (field "saturday" (maybe workday)) (field "sunday" (maybe workday))
