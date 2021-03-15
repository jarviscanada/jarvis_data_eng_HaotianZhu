
-- Inspect the table schema
\d+ retail;


-- Show first 10 rows
SELECT * FROM retail limit 10;



-- Check # of records
SELECT count(*) FROM retail;

-- number of clients (e.g. unique client ID)
SELECT count(distinct(customer_id)) FROM retail;


-- invoice date range (e.g. max/min dates)
SELECT max(invoice_date) AS max, min(invoice_date) AS min
FROM retail;


-- number of SKU/merchants (e.g. unique stock code)
SELECT count(distinct(stock_code)) FROM retail;


select avg(t.total)
from (
SELECT invoice_no, sum(unit_price*quantity) as total
FROM retail
GROUP BY invoice_no
) t
where t.total > 0




--  Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT sum(unit_price*quantity)
FROM retail

-- Calculate total revenue (e.g. sum of unit_price * quantity)

SELECT to_char(invoice_date, 'YYYYMM') as d, sum(unit_price*quantity)
FROM retail
group by d order by d ASC