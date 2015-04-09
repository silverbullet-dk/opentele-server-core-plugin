ALTER TABLE PATIENT ALTER COLUMN CPR nvarchar(128) NOT NULL
GO


DROP INDEX [patient_cpr_idx] ON [dbo].[audit_log_entry]
GO
ALTER TABLE AUDIT_LOG_ENTRY ALTER COLUMN patient_cpr nvarchar(128)
GO
CREATE INDEX [patient_cpr_idx] ON [dbo].[audit_log_entry]([patient_cpr])
GO

DROP INDEX [monkit_deptid_name_unique] ON [dbo].[monitor_kit]
GO
ALTER TABLE MONITOR_KIT ALTER COLUMN name nvarchar(128)
GO
CREATE INDEX [monkit_deptid_name_unique] ON [dbo].[monitor_kit]([department_id], [name])
GO


DROP INDEX [ptgroup_deptid_name_unique] ON [dbo].[patient_group]
GO
ALTER TABLE PATIENT_GROUP ALTER COLUMN name nvarchar(128)
GO
CREATE INDEX [ptgroup_deptid_name_unique] ON [dbo].[patient_group]([department_id], [name])
GO

TRUNCATE TABLE real_timectg;
GO

EXEC sp_rename 'real_timectg', 'real_time_ctg';
GO

ALTER TABLE [dbo].[real_time_ctg] ADD [patient_id] BIGINT NOT NULL
GO

ALTER TABLE [dbo].[real_time_ctg]
  ADD CONSTRAINT [real_time_ctg_patient_FK]
  FOREIGN KEY ([patient_id])
  REFERENCES [dbo].[patient] ([id])
GO

CREATE INDEX [patient_id_created_date_idx] ON [dbo].[real_time_ctg]([patient_id], [created_date]);
GO
