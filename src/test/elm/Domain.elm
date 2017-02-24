module Domain exposing (..)

{-| Domain module.
@docs Building, Company, ContactInformation, ContactPerson, EmployedPerson, EnergyRequirements, Location, LogEntry, NewLocation, NewNotification, NewWorkEntry, Notification, NotificationAction, NotificationStatus, Occupation, Person, Ship, ShipArea, Signup, TimeOfDay, User, WorkEntry, Workday, Worker, Workweek
-}

import Date


{-| -}
type alias Building =
    { id : Int
    , code : String
    , description : String
    }


{-| A company.
-}
type alias Company =
    { id : Int
    , name : String
    }


{-| Email and phone number.
-}
type alias ContactInformation =
    { email : String
    , phoneNumber : String
    }


{-| A person with contact information.
-}
type alias ContactPerson =
    { employedPerson : EmployedPerson
    , contactInformation : ContactInformation
    }


{-| A person with a company (the person's employer).
-}
type alias EmployedPerson =
    { person : Person
    , company : Company
    }


{-| The "energies" (gases, compressed air, hot works) a worker needs.
-}
type alias EnergyRequirements =
    { oxyacetylene : Bool
    , compositeGas : Bool
    , argon : Bool
    , compressedAir : Bool
    , hotWorks : Bool
    }


{-| A location always includes a building. It can also optionally include a ship and a ship area.
-}
type alias Location =
    { building : Building
    , ship : Maybe Ship
    , shipArea : Maybe ShipArea
    }


{-| A logged event in a notification's history.
-}
type alias LogEntry =
    { id : Int
    , action : NotificationAction
    , createdAt : Date.Date
    , createdBy : User
    }


{-| A location always includes a building. It can also optionally include a ship and a ship area.
-}
type alias NewLocation =
    { building : Int
    , ship : Maybe Int
    , shipArea : Maybe Int
    }


{-| A notification about workers and their work hours.
-}
type alias NewNotification =
    { status : NotificationStatus
    , yardContact : Int
    , siteForeman : Int
    , additionalInformation : String
    , workWeek : Workweek
    , workEntries : List NewWorkEntry
    }


{-| Entry about one worker, his/her location and energy requirements.
-}
type alias NewWorkEntry =
    { worker : Int
    , location : NewLocation
    , occupation : Occupation
    , energyRequirements : EnergyRequirements
    }


{-| A notification about workers and their work hours.
-}
type alias Notification =
    { id : Int
    , status : NotificationStatus
    , yardContact : ContactPerson
    , siteForeman : ContactPerson
    , additionalInformation : String
    , workWeek : Workweek
    , workEntries : List WorkEntry
    , log : List LogEntry
    }


{-| The possible actions for a LogEntry.
-}
type NotificationAction
    = Create
    | Send
    | Approve
    | Reject
    | Edit


{-| The state a notification is in.
-}
type NotificationStatus
    = Approved
    | Draft
    | Rejected
    | WaitingForYardContact
    | WaitingForProductionManager


{-| The task which a worker is performing.
-}
type Occupation
    = Fitter
    | Welder
    | PipeFitter
    | ProjectManager
    | Other


{-| A person's basic information (currently only name).
-}
type alias Person =
    { id : Int
    , firstName : String
    , lastName : String
    }


{-| -}
type alias Ship =
    { id : Int
    , code : String
    , description : String
    }


{-| -}
type alias ShipArea =
    { id : Int
    , code : String
    , description : String
    }


{-| Signup info for a user.
-}
type alias Signup =
    { firstName : String
    , lastName : String
    , email : String
    , phoneNumber : String
    , company : Company
    , password : String
    }


{-| The time of day without a date.
-}
type alias TimeOfDay =
    { hour : Int
    , minute : Int
    }


{-| A user of the application.
-}
type alias User =
    { contactPerson : ContactPerson
    , confirmed : Bool
    , token : String
    , password : String
    }


{-| Entry about one worker, his/her location and energy requirements.
-}
type alias WorkEntry =
    { id : Int
    , worker : Worker
    , location : Location
    , occupation : Occupation
    , energyRequirements : EnergyRequirements
    }


{-| The working time of one day.
-}
type alias Workday =
    { startTime : TimeOfDay
    , endTime : TimeOfDay
    }


{-| A person with a worker key code.
-}
type alias Worker =
    { employedPerson : EmployedPerson
    , keyCode : String
    }


{-| A working week number and days with times.
-}
type alias Workweek =
    { weekNumber : Int
    , monday : Maybe Workday
    , tuesday : Maybe Workday
    , wednesday : Maybe Workday
    , thursday : Maybe Workday
    , friday : Maybe Workday
    , saturday : Maybe Workday
    , sunday : Maybe Workday
    }
