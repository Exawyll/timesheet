heroku pg:psql --app timesheet-ag DATABASE

\COPY jhi_user TO 'C:\Users\WYLLIAM\user_export.csv' WITH (FORMAT csv, DELIMITER ',',  HEADER true);

SELECT w.year, w.label, p.name, u.first_name, a.time_spent from activity a
INNER JOIN project p ON p.id = a.project_id
INNER JOIN week w ON w.id = a.week_id
INNER JOIN jhi_user u ON u.id = a.user_id