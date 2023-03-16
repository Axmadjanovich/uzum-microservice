do '
declare
begin
    if not exists(select 1 from sales s where s.id = 1) then
        insert into sales(expiration_date,price,product_id) values (''2023-03-18 00:00:00'',5000,2);
    end if;
end;
' language plpgsql;