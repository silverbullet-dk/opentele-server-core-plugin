CREATE TABLE [dbo].[real_timectg] (
  [id] BIGINT IDENTITY NOT NULL,
  [version] BIGINT NOT NULL,
  [created_by] NVARCHAR(1024),
  [created_date] datetime2(7),
  [modified_by] NVARCHAR(1024),
  [modified_date] datetime2(7),
  [xml] VARCHAR(max) NOT NULL,
  [soap_action] NVARCHAR(1024) NOT NULL,
  CONSTRAINT [real_timectg_PK] PRIMARY KEY ([id]))
GO
