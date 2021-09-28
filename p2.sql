--Place code for Project Phase 2 here
--Created by: Brigid Auclair, Katherine Cariglia, Gabi Tessier

--Create WinnerOrder View
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
