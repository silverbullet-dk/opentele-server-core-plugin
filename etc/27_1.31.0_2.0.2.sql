ALTER TABLE [dbo].[patient_help_info] ADD CONSTRAINT [FK_PT_HELP_INF_IMG] FOREIGN KEY ([help_image_id]) REFERENCES [dbo].[help_image] ([id])
GO

CREATE INDEX [IDX_PT_HELP_INF_IMG] ON [dbo].[patient_help_info]([help_image_id])
GO