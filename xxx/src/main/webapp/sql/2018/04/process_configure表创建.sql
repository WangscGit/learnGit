USE [CMS_Cloudy_database]
GO

/****** Object:  Table [dbo].[process_configure]    Script Date: 04/18/2018 14:48:29 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[process_configure](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[process_def_id] [varchar](50) NULL,
	[process_type] [varchar](50) NULL,
	[normal_attrs] [varchar](50) NULL,
	[quality_attrs] [varchar](50) NULL,
	[design_attrs] [varchar](50) NULL,
	[purchase_attrs] [varchar](50) NULL,
	[task_key] [varchar](50) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


