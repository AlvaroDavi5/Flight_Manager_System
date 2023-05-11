
create table airports (
	"id" serial not null,
	"ICAO" varchar(10) not null,
	"IATA" varchar(10) not null,
	"isAirstripFree" boolean default true,
	"isSafeToFlight" boolean default true,
	"gatesAmount" integer not null,

	primary key (id)
);

create table gates (
	"id" serial not null,
	"gateNumber" integer not null,
	"isFreeToDock" boolean default true,
	"isOpenToBoarding" boolean default false,
	"boardingDuration" integer default 120,
	"flightCode" varchar(15) default null,

	primary key (id)
);

create table flights (
	"id" serial not null,
	"code" varchar(15) not null,
	"gateNumber" integer default 0,
	"flightStatus" varchar(20) not null,
	"logisticStatus" varchar(20) not null,
	"departureAirportCode" varchar(10) default null,
	"departureHorizontalDistance" integer default 0,
	"departureVerticalDistance" integer default 0,
	"departureAirportCandidates" integer default 0,
	"departureTime" integer default 0,
	"arrivalAirportCode" varchar(10) default null,
	"arrivalHorizontalDistance" integer default 0,
	"arrivalVerticalDistance" integer default 0,
	"arrivalAirportCandidates" integer default 0,
	"arrivalTime" integer default 0,

	primary key (id)
);
