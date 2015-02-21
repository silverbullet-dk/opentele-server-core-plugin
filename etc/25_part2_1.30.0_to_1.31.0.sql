
-- Manuel konvertering af continuous_blood_sugar_event.units

-- Find units som skal divideres med 100 -- (OBS: type er String, derfor lavpraktisk løsning - skal kun anvendes i test)
SELECT id, [units], insulin_type
 FROM [dbo].[continuous_blood_sugar_event]
  where [units] is not null;

-- Opdater units: OBS: TIlpas nedenstående med ID'er mv. fundet foroven.
/*
update continuous_blood_sugar_event set units = 2
  where id in (20074, 24328, 24579)
  and units is not null
  and units = 200
  and insulin_type is not null;

update continuous_blood_sugar_event set units = 10
  where id in (28381)
  and units is not null
  and units = 1000
  and insulin_type is not null;
*/

-- Check at data er korrekte nu:
  SELECT id, [units], insulin_type
 FROM [dbo].[continuous_blood_sugar_event]
  where [units] is not null;

