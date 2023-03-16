do '
begin
    if not exists(select 12 from category c where c.id = 12) then
        insert into category(id, name, parent_id) values (12, ''Maishiy texnika'', 3);
    end if;
end;
'