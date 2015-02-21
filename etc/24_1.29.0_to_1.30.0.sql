-- Drop FK
-- dropForeignKeyConstraint(baseTableName: "continuous_blood_sugar_measurement", constraintName: "FK_CGM_MEASUREMENT")
ALTER TABLE [dbo].[continuous_blood_sugar_measurement] drop constraint [FK_CGM_MEASUREMENT];

-- renameTable(oldTableName: "continuous_blood_sugar_measurement", newTableName: "continuous_blood_sugar_event")
EXEC sp_rename 'continuous_blood_sugar_measurement', 'continuous_blood_sugar_event';

-- renameColumn(tableName: "continuous_blood_sugar_event", oldColumnName: "value", newColumnName: "glucose_value_inmmol_perl")
EXEC sp_RENAME 'continuous_blood_sugar_event.value' , 'glucose_value_inmmol_perl', 'COLUMN';

-- dropNotNullConstraint(tableName: "continuous_blood_sugar_event", columnName:"glucose_value_inmmol_perl", columnDataType: '${double.type}')
ALTER TABLE [dbo].[continuous_blood_sugar_event] ALTER COLUMN [glucose_value_inmmol_perl] float NULL;
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [class] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [duration_in_minutes] INT;
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [exercise_type] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [exercise_intensity] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [impending_ness] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [insulin_type] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [units] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [food_type] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [carbo_grams] nvarchar(1024);
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [state_of_health] nvarchar(1024);

ALTER TABLE [dbo].[measurement] DROP COLUMN [cgm_graphs_created];

ALTER TABLE [dbo].[continuous_blood_sugar_event]
  ADD CONSTRAINT [FK_CGM_MEASUREMENT]
  FOREIGN KEY ([measurement_id])
  REFERENCES [dbo].[measurement] ([id]);

UPDATE [dbo].[continuous_blood_sugar_event] SET [class] = 'org.opentele.server.model.cgm.ContinuousBloodSugarMeasurement';

CREATE INDEX [record_num_idx] ON [dbo].[continuous_blood_sugar_event]([record_number]);