ALTER TABLE Part_PrimaryAttributes
ADD IsInsert bit;
ALTER TABLE Part_PrimaryAttributes
ADD EnglishName nvarchar(50);

ALTER TABLE Part_PrimaryAttributes
ADD IsApply bit;

update Part_PrimaryAttributes set IsInsert='false';
update Part_PrimaryAttributes set IsApply='false';