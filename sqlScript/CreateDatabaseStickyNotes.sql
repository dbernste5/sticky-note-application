create database StickyNotes;
use StickyNotes;

create table users
(
	UserID int primary key not null auto_increment,
	Username varchar(15) unique not null,
    Password varchar(15) not null,
	FirstName varchar(50) not null,
    LastName varchar(50) not null,
    Email varchar(25) not null,
    Phone varchar(15),
    DateGenerated datetime default current_timestamp
);

create table basicStickies(
   StickyId int primary key auto_increment,
   UserID int not null,
   Title varchar(55) not null,
   Body varchar(500) not null,
   constraint fk_sticky_userID foreign key (UserID) references users(UserID)
);
