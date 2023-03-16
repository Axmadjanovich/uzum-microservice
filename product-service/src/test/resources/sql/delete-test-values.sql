do '
declare
begin
    delete from product where category_id = 1;
    delete from category where id = 1 and name = ''kg'' and parent_id is null;
end;
' language plpgsql;