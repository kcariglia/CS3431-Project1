--Place code for Project Phase 2 here
--Created by: Brigid Auclair, Katherine Cariglia, Gabi Tessier

--Create WinnerOrder View
create or replace view WinnerOrder as
select winOrder, title, category, AP.firstName || ' ' || AP.lastName as ARTIST, 
W.firstName || ' ' || W.lastName as WINNER
from (select * from Artwork A join Participant P on A.creatorEmail = P.email) AP 
join Participant W on AP.winnerEmail = W.email
order by winOrder;
