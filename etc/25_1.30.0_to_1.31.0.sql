/* Add indicated event to cont. blood sugar */
ALTER TABLE [dbo].[continuous_blood_sugar_event] ADD [indicated_event] nvarchar(1024)
GO


/* Help - functionality */
CREATE TABLE [dbo].[help_image] (
  [id] BIGINT IDENTITY NOT NULL,
  [filename] NVARCHAR(512),
  [version] BIGINT NOT NULL,
  [created_by] NVARCHAR(1024),
  [created_date] datetime2(7),
  [modified_by] NVARCHAR(1024),
  [modified_date] datetime2(7),
  CONSTRAINT [help_imagePK] PRIMARY KEY (id)
)
GO

CREATE TABLE [dbo].[help_info] (
  [id] BIGINT IDENTITY NOT NULL,
  [help_image_id] BIGINT,
  [text] NVARCHAR(max),
  [version] BIGINT NOT NULL,
  [created_by] NVARCHAR(1024),
  [created_date] datetime2(7),
  [modified_by] NVARCHAR(1024),
  [modified_date] datetime2(7),
  CONSTRAINT [help_infoPK] PRIMARY KEY (id)
)
GO

Create TABLE [dbo].[patient_help_info] (
  [id] BIGINT IDENTITY NOT NULL,
  [help_image_id] BIGINT,
  [text] NVARCHAR(max),
  [version] BIGINT NOT NULL,
  [created_by] NVARCHAR(1024),
  [created_date] datetime2(7),
  [modified_by] NVARCHAR(1024),
  [modified_date] datetime2(7),
  CONSTRAINT [patient_help_PK] PRIMARY KEY (id)
)
GO

ALTER TABLE [dbo].[patient_questionnaire_node] ADD [help_info_id] BIGINT
GO

ALTER TABLE [dbo].[questionnaire_node] ADD [help_info_id] BIGINT
GO

ALTER TABLE [dbo].[help_info] ADD CONSTRAINT [FK_HELP_INF_IMG] FOREIGN KEY ([help_image_id]) REFERENCES [dbo].[help_image] ([id])
GO

CREATE INDEX [IDX_HELP_INF_IMG] ON [dbo].[help_info]([help_image_id])
GO

ALTER TABLE [dbo].[patient_questionnaire_node] ADD CONSTRAINT [FK_PQN_HELP_INFO] FOREIGN KEY ([help_info_id]) REFERENCES [dbo].[patient_help_info] ([id])
GO

CREATE INDEX [IDX_PQN_HELP_INFO] ON [dbo].[patient_questionnaire_node]([help_info_id])
GO

ALTER TABLE [dbo].[questionnaire_node] ADD CONSTRAINT [FK_QNODE_HELP_INFO] FOREIGN KEY ([help_info_id]) REFERENCES [dbo].[help_info] ([id])
GO

CREATE INDEX [IDX_QNODE_HELP_INFO] ON [dbo].[questionnaire_node]([help_info_id])
GO
