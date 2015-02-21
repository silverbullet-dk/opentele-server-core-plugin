CREATE TABLE [dbo].[user_preference] (
  [id] BIGINT IDENTITY NOT NULL,
  [version] BIGINT NOT NULL,
  [created_by] NVARCHAR(1024),
  [created_date] datetime2(7),
  [modified_by] NVARCHAR(1024),
  [modified_date] datetime2(7),

  [clinician_id] BIGINT NOT NULL,
  [preference] VARCHAR(1024) NOT NULL,
  [value] NVARCHAR(max) NOT NULL,
  CONSTRAINT [user_preference_PK] PRIMARY KEY ([id]))
GO
