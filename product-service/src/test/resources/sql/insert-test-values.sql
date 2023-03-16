do '
declare
begin
    if not exists(select 1 from category c where c.id = 1) then
        insert into category(id, name, parent_id) values (1, ''kg'', null);
    end if;
end;
' language plpgsql;