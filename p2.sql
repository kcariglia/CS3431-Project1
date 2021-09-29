--Place code for Project Phase 2 here
--Created by: Brigid Auclair, Katherine Cariglia, Gabi Tessier

--1A
create or replace view WinnerOrder as
select winOrder, title, category, AP.firstName || ' ' || AP.lastName as ARTIST, 
W.firstName || ' ' || W.lastName as WINNER
from (select * from Artwork A join Participant P on A.creatorEmail = P.email) AP 
join Participant W on AP.winnerEmail = W.email
order by winOrder;

--1B
select category, count(title) as NumItemsWon
from WinnerOrder
group by rollup (WinnerOrder.category);

--2
set serveroutput on;
create or replace procedure FavoriteArtwork(artwork_ID in number) as
total number;
cursor a is
select count(email)
from RankList
where RankList.artworkID = artwork_ID 
group by RankList.email;
begin 
open a;
fetch a into total;
if a%notfound then
    raise_application_error(-20001, 'An error was encountered – ' || SQLCODE || ' -ERROR-' || SQLERRM);
end if;
dbms_output.put_line('Artwork #' || artwork_ID || ': Selected ' || total || ' times.');
end FavoriteArtwork;

--3
create or replace trigger OnlyTicketedRanking 
before insert or update on RankList 
for each row
Declare tickets char(1);
Begin
    Select hasTicket into tickets from Participant; 
    IF (tickets = 'N') then
        RAISE_APPLICATION_ERROR(-20004, 'Invalid Ticket');  
    END IF;      
End;

--4
create or replace trigger DeleteWonArtwork
after insert or update on Artwork
for each row
when (new.winOrder is not null)
begin
	delete from RankList where artworkID = :new.artworkID;
end;
/

--5
create or replace trigger MemberConstraint
before insert or update of memberID on Participant
Declare     
temp number(4);
cursor m is select yearJoined, hasExpired from Participant where Participant.memberID is not null;
Begin     
Select extract(year from sysdate) into temp from dual;
for rec in m loop
    if (rec.yearJoined is null) then
    rec.yearJoined := temp;
    rec.hasExpired := 'N';
    end if;
end loop;
end;
/
