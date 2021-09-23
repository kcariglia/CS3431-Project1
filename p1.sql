--Project 1 group 2

drop table Ranking;
drop table TwoD;
drop table Pottery;
drop table Artworks;
drop table Gallery;
drop table Building;
drop table Participants;

create table Participants(
participantEmailAddress varchar2(25) primary key,
firstName varchar2(15),
lastName varchar2(15),
phoneNumber number(10),
city varchar2(20), 
state char(2),
ticket char(1) check(ticket = 't' or ticket = 'f'),
memberID varchar(10) unique,
yearJoined number(4),
expired char(1) check(expired = 't' or expired = 'f'),
numArtEvents number(3));

create table Building(
buildingName varchar2(20) primary key,
street varchar2(20),
city varchar2(20),
state char(2),
zipcode varchar2(5));

create table Gallery(
galleryName varchar2(20),
buildingName varchar2(20),
maxCapacity number(4),
constraint galleryfk foreign key (buildingName) references Building(buildingName),
constraint gallerypk primary key (galleryName, buildingName));

create table Artworks(
artID varchar2(10) primary key,
title varchar2(25),
price number(4,2),
galleryName varchar2(20),
buildingName varchar2(20),
participantEmailAddress varchar2(25) references Participants(participantEmailAddress),
category varchar2(15),
constraint Artworks_un unique (artID, category),
foreign key (galleryName, buildingName) references Gallery(galleryName, buildingName));

create table TwoD(
artID varchar2(10) primary key,
category varchar2(15) default 'TwoD' not null,
medium varchar2(25),
constraint TwoDCategoryVal check (category in ('TwoD')),
constraint TwoD_FK foreign key (artID, category) references Artworks(artID, category)
);

create table Pottery(
artID varchar2(10) primary key,
category varchar2(15) default 'Pottery' not null,
clayBodyUsed varchar2(25),
constraint PotteryCategoryVal check (category in ('Pottery')),
constraint Pottery_FK foreign key (artID, category) references Artworks(artID, category)
);

create table Ranking(
participantEmailAddress varchar2(25) not null,
artID varchar2(10) not null,
rank number(2) check (rank <= 20) not null,
constraint Ranking_PK primary key (participantEmailAddress, artID),
constraint Ranking_UQ unique (participantEmailAddress, rank),
constraint Ranking_ParticipantFK foreign key (participantEmailAddress) references Participants(participantEmailAddress),
constraint Ranking_ArtworkFK foreign key (artID) references Artworks(artID)
);
