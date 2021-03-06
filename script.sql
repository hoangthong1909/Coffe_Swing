USE [Coffee]
GO
/****** Object:  StoredProcedure [dbo].[DT_THONGKEKHOANG]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[DT_THONGKEKHOANG] (@ngayBD date,@ngayKT date)
AS BEGIN
	SELECT
		Sum(hv.[Quantity]*[Price]) as Tien,
		(od.DateOrder) as Ngay
	FROM [Order] od
		JOIN [OrderDetail] hv ON od.IDOrder=hv.IDOrder
		JOIN [Product] cd ON cd.IDProduct=hv.IDProduct
where  DateOrder between @ngayBD  and @ngayKT
--where @ngayBD Between @ngayKT
group by (od.DateOrder)
END






GO
/****** Object:  StoredProcedure [dbo].[DT_THONGKENAM]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[DT_THONGKENAM] (@Nam int)
AS BEGIN
	SELECT
		Sum(hv.[Quantity]*[Price]) as Tien,
		Month(od.DateOrder) as Thang
	FROM [Order] od
		JOIN [OrderDetail] hv ON od.IDOrder=hv.IDOrder
		JOIN [Product] cd ON cd.IDProduct=hv.IDProduct
where Year([DateOrder])=@Nam
group by Month(od.DateOrder)

END


GO
/****** Object:  StoredProcedure [dbo].[DT_THONGKENGAY]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[DT_THONGKENGAY] (@NGay date)
AS BEGIN
	SELECT
		Sum(hv.[Quantity]*[Price]) as Tien,
		([TimeOder])
	FROM [Order] od
		JOIN [OrderDetail] hv ON od.IDOrder=hv.IDOrder
		JOIN [Product] cd ON cd.IDProduct=hv.IDProduct
		where ([DateOrder])=@NGay
	GROUP BY ([TimeOder])
END



GO
/****** Object:  StoredProcedure [dbo].[DT_THONGKETHANG]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[DT_THONGKETHANG] (@thang int)
AS BEGIN
	SELECT
		Sum(hv.[Quantity]*[Price]) as Tien,
		(od.DateOrder) as Ngay
	FROM [Order] od
		JOIN [OrderDetail] hv ON od.IDOrder=hv.IDOrder
		JOIN [Product] cd ON cd.IDProduct=hv.IDProduct
where Month([DateOrder])=@thang
group by (od.DateOrder)

END






GO
/****** Object:  StoredProcedure [dbo].[getBillHuyNam]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getBillHuyNam] (@nam int)
AS BEGIN
select count(IDOrder) as tongBillHuy from [Order] where [Status]=3 and year(DateOrder)=@nam
END


GO
/****** Object:  StoredProcedure [dbo].[getBillHuyNgay]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getBillHuyNgay] (@ngay date)
AS BEGIN
select count(IDOrder) as tongBillHuy from [Order] where [Status]=3 and (DateOrder)=@ngay
END


GO
/****** Object:  StoredProcedure [dbo].[getListBysendMailNgay]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getListBysendMailNgay] (@ngay date)
AS BEGIN
select DISTINCT (o.IDOrder) as IDHD,o.UsernameEMP,o.DateOrder,o.TimeOder,o.Reason from [Order] o join OrderDetail od on o.IDOrder=od.IDOrder
where o.[Status]=3 and (o.DateOrder) = @ngay
END


GO
/****** Object:  StoredProcedure [dbo].[getListHoaDon]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[getListHoaDon]
AS BEGIN
select DISTINCT (o.IDOrder) as IDHD,e.NameEMP,o.DateOrder,o.TimeOder,o.Reason,Sum(od.Quantity*p.Price) as TongTien,o.Status
 from [Order] o 
 join OrderDetail od on o.IDOrder=od.IDOrder
 join Product p on od.IDProduct=p.IDProduct
 left join Employee e on e.UsernameEMP=o.UsernameEMP
 group by (o.IDOrder),e.NameEMP,o.DateOrder,o.TimeOder,o.Reason,o.Status
END
GO
/****** Object:  StoredProcedure [dbo].[getListHoaDonThang]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getListHoaDonThang] (@thang int)
AS BEGIN
select DISTINCT (o.IDOrder) as IDHD,e.NameEMP,o.DateOrder,o.TimeOder,o.Reason,Sum(od.Quantity*p.Price) as TongTien,o.Status
 from [Order] o 
 join OrderDetail od on o.IDOrder=od.IDOrder
 join Product p on od.IDProduct=p.IDProduct
 left join Employee e on e.UsernameEMP=o.UsernameEMP
 where Month(o.DateOrder)=@thang
 group by (o.IDOrder),e.NameEMP,o.DateOrder,o.TimeOder,o.Reason,o.Status
END
GO
/****** Object:  StoredProcedure [dbo].[getTongMvaTongHD]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[getTongMvaTongHD] (@ngay date)
AS BEGIN
Select  (Select  Sum(oo.Quantity) FROm OrderDetail oo join dbo.[Order] o on oo.IDOrder = o.IDOrder
where (o.DateOrder) = @ngay) as tongM, 
(Select COUNT(DISTINCT oo.IDOrder) as tongHD FROm OrderDetail oo join dbo.[Order] o on oo.IDOrder = o.IDOrder
where (DateOrder) =@ngay) as TongHD
END
GO
/****** Object:  StoredProcedure [dbo].[getTongMvaTongHDKhoang]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getTongMvaTongHDKhoang] (@ngayBD Date,@ngayKT date)
AS BEGIN
Select  (Select  Sum(oo.Quantity) FROm OrderDetail oo join dbo.[Order] o on oo.IDOrder = o.IDOrder
where  DateOrder between @ngayBD  and @ngayKT) as tongM, 
(Select  COUNT(IDOrder) as tongHD FROm [Order]
where  DateOrder between @ngayBD  and @ngayKT) as TongHD
END


GO
/****** Object:  StoredProcedure [dbo].[getTongMvaTongHDNam]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[getTongMvaTongHDNam] (@nam int)
AS BEGIN
Select  (Select  Sum(oo.Quantity) FROm OrderDetail oo join dbo.[Order] o on oo.IDOrder = o.IDOrder
where year(o.DateOrder) =@nam) as tongM, 
(Select  COUNT(IDOrder) as tongHD FROm [Order]
where year(DateOrder) =@nam) as TongHD
END



GO
/****** Object:  StoredProcedure [dbo].[getTongMvaTongHDThang]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[getTongMvaTongHDThang] (@thang int)
AS BEGIN
Select  (Select  Sum(oo.Quantity) FROm OrderDetail oo join dbo.[Order] o on oo.IDOrder = o.IDOrder
where month(o.DateOrder) =@thang) as tongM, 
(Select  COUNT(IDOrder) as tongHD FROm [Order]
where month(DateOrder) =@thang) as TongHD
END



GO
/****** Object:  StoredProcedure [dbo].[sendmailNam]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[sendmailNam] (@nam int)
AS BEGIN
select DISTINCT (o.IDOrder) as IDHD,o.UsernameEMP,o.DateOrder,o.TimeOder,o.Reason from [Order] o join OrderDetail od on o.IDOrder=od.IDOrder
where o.[Status]=3 and year(o.DateOrder) =@nam
END


GO
/****** Object:  StoredProcedure [dbo].[sendmailNgay]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROC [dbo].[sendmailNgay] (@ngay date)
AS BEGIN
select DISTINCT (o.IDOrder) as IDHD,o.UsernameEMP,o.DateOrder,o.TimeOder,o.Reason from [Order] o join OrderDetail od on o.IDOrder=od.IDOrder
where o.[Status]=3 and (o.DateOrder) =@ngay
END


GO
/****** Object:  Table [dbo].[Admin]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admin](
	[Username] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](150) NULL,
 CONSTRAINT [PK_Admin] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Area]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Area](
	[IDArea] [int] IDENTITY(1,1) NOT NULL,
	[AreaName] [nvarchar](50) NULL,
	[MaxTable] [int] NULL,
 CONSTRAINT [PK_Area] PRIMARY KEY CLUSTERED 
(
	[IDArea] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customer]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customer](
	[IDCust] [varchar](10) NOT NULL,
	[CusName] [nvarchar](50) NOT NULL,
	[DateAdd] [date] NOT NULL,
	[DateEnd] [date] NOT NULL,
	[Phone] [varchar](15) NOT NULL,
	[Email] [nvarchar](150) NOT NULL,
	[Discount] [int] NOT NULL,
	[Status] [bit] NOT NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[IDCust] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Employee](
	[UsernameEMP] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](150) NULL,
	[NameEMP] [nvarchar](50) NULL,
	[Phone] [varchar](15) NULL,
	[Birthday] [date] NULL,
	[Address] [nvarchar](150) NULL,
	[Sex] [bit] NULL,
	[Email] [varchar](50) NULL,
	[Image] [nvarchar](50) NULL,
	[Status] [bit] NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[UsernameEMP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Order]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Order](
	[IDOrder] [varchar](10) NOT NULL,
	[DateOrder] [date] NULL,
	[TimeOder] [time](7) NULL,
	[UsernameEMP] [nvarchar](50) NULL,
	[IDCust] [varchar](10) NULL,
	[Status] [int] NOT NULL,
	[Reason] [nvarchar](100) NULL,
	[NamePromo] [nvarchar](50) NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[IDOrder] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[IDOrder] [varchar](10) NULL,
	[IDProduct] [varchar](10) NULL,
	[IDTable] [varchar](10) NULL,
	[Quantity] [int] NULL,
	[Note] [nvarchar](200) NULL,
	[Reason] [nchar](10) NULL,
	[Status] [bit] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Product]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Product](
	[IDProduct] [varchar](10) NOT NULL,
	[ProductName] [nvarchar](50) NULL,
	[Price] [float] NULL,
	[Image] [nvarchar](50) NULL,
	[Status] [bit] NULL,
	[IDType] [int] NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[IDProduct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ProductType]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProductType](
	[IDType] [int] NOT NULL,
	[TypeName] [nvarchar](50) NULL,
	[Size] [varchar](10) NULL,
 CONSTRAINT [PK_ProductType] PRIMARY KEY CLUSTERED 
(
	[IDType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Promotions]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotions](
	[IDPromo] [int] IDENTITY(1,1) NOT NULL,
	[NamePromo] [nvarchar](50) NULL,
	[DiscountPromo] [int] NULL,
	[StartPromo] [date] NULL,
	[EndPromo] [date] NULL,
	[Description] [nvarchar](200) NULL,
	[Status] [bit] NULL,
 CONSTRAINT [PK_Promotions] PRIMARY KEY CLUSTERED 
(
	[IDPromo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Table]    Script Date: 11/29/2021 2:03:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Table](
	[IDTable] [varchar](10) NOT NULL,
	[Location] [int] NULL,
	[Status] [int] NULL,
	[IDArea] [int] NULL,
	[TableGroup] [nvarchar](50) NULL,
 CONSTRAINT [PK_Table] PRIMARY KEY CLUSTERED 
(
	[IDTable] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Admin] ([Username], [Password]) VALUES (N'Admin', N'1')
SET IDENTITY_INSERT [dbo].[Area] ON 

INSERT [dbo].[Area] ([IDArea], [AreaName], [MaxTable]) VALUES (1, N'1', 20)
INSERT [dbo].[Area] ([IDArea], [AreaName], [MaxTable]) VALUES (2, N'2', 20)
INSERT [dbo].[Area] ([IDArea], [AreaName], [MaxTable]) VALUES (4, N'3', NULL)
SET IDENTITY_INSERT [dbo].[Area] OFF
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH01', N'Mạnh PHẠM', CAST(N'2021-10-10' AS Date), CAST(N'2021-10-11' AS Date), N'0988307480', N'manh@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH02', N'Mạnh', CAST(N'2021-11-11' AS Date), CAST(N'2021-11-12' AS Date), N'0768373682', N'manh1@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH03', N'Thành', CAST(N'2021-12-12' AS Date), CAST(N'2022-12-01' AS Date), N'0932472623', N'Thanh@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH04', N'Vương', CAST(N'2021-12-13' AS Date), CAST(N'2022-12-02' AS Date), N'0932472643', N'Thanh@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH05', N'Thông', CAST(N'2021-12-20' AS Date), CAST(N'2022-12-20' AS Date), N'0936472623', N'Thong@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH06', N'Tú', CAST(N'2021-01-20' AS Date), CAST(N'2022-02-20' AS Date), N'0936452623', N'TU@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH07', N'Nga', CAST(N'2022-08-30' AS Date), CAST(N'2022-09-30' AS Date), N'0931472623', N'Nga@gmail.com', 3, 0)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH08', N'Thành Nga', CAST(N'2022-03-20' AS Date), CAST(N'2022-04-20' AS Date), N'0936471623', N'NgaThanh@gmail.com', 3, 0)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH09', N'Tu Thống', CAST(N'2022-04-20' AS Date), CAST(N'2022-05-20' AS Date), N'0931171623', N'Thongtu@gmail.com', 3, 1)
INSERT [dbo].[Customer] ([IDCust], [CusName], [DateAdd], [DateEnd], [Phone], [Email], [Discount], [Status]) VALUES (N'KH10', N'Vương TÚ', CAST(N'2022-03-20' AS Date), CAST(N'2022-04-20' AS Date), N'0936471623', N'tunga123@gmail.com', 3, 0)
INSERT [dbo].[Employee] ([UsernameEMP], [Password], [NameEMP], [Phone], [Birthday], [Address], [Sex], [Email], [Image], [Status]) VALUES (N'MANHNV', N'123', N'sdas', N'0988307800', CAST(N'2021-11-17' AS Date), N'ádas', 1, N'sad@gmail.com', NULL, 0)
INSERT [dbo].[Employee] ([UsernameEMP], [Password], [NameEMP], [Phone], [Birthday], [Address], [Sex], [Email], [Image], [Status]) VALUES (N'sdđ', N'aaa', N'Vưng', N'09878767', CAST(N'2021-11-16' AS Date), N'sad', 1, N'sdaasd', NULL, 0)
INSERT [dbo].[Employee] ([UsernameEMP], [Password], [NameEMP], [Phone], [Birthday], [Address], [Sex], [Email], [Image], [Status]) VALUES (N'Thằn', N'1', N'Thằn', N'0978346813', CAST(N'2002-01-22' AS Date), N'NGhệ An', 1, N'Thamh@gmail.com', N'67407534_465206764333003_5213630654238949376_n.jpg', 0)
INSERT [dbo].[Employee] ([UsernameEMP], [Password], [NameEMP], [Phone], [Birthday], [Address], [Sex], [Email], [Image], [Status]) VALUES (N'THANHNV', N'1', N'Thằn', N'0978346813', CAST(N'2002-01-22' AS Date), N'NGhệ An', 1, N'Thamh@gmail.com', N'67407534_465206764333003_5213630654238949376_n.jpg', 0)
INSERT [dbo].[Employee] ([UsernameEMP], [Password], [NameEMP], [Phone], [Birthday], [Address], [Sex], [Email], [Image], [Status]) VALUES (N'VUONGNV', N'1', N'Vươn', N'098257682', CAST(N'2002-11-13' AS Date), N'Phú Thọ', 0, N'vuuong@gmail.co', NULL, 0)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD001', CAST(N'2021-11-25' AS Date), CAST(N'19:43:00' AS Time), N'THANHNV', N'KH01', 2, NULL, NULL)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD002', CAST(N'2021-11-28' AS Date), CAST(N'14:37:00' AS Time), N'THANHNV', NULL, 1, NULL, NULL)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD003', CAST(N'2021-11-28' AS Date), CAST(N'14:38:00' AS Time), N'THANHNV', NULL, 1, NULL, NULL)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD004', CAST(N'2021-11-28' AS Date), CAST(N'14:42:00' AS Time), N'THANHNV', NULL, 1, NULL, NULL)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD005', CAST(N'2021-11-28' AS Date), CAST(N'15:53:00' AS Time), NULL, NULL, 3, N'Nhân viên Order ngu ', NULL)
INSERT [dbo].[Order] ([IDOrder], [DateOrder], [TimeOder], [UsernameEMP], [IDCust], [Status], [Reason], [NamePromo]) VALUES (N'OD006', CAST(N'2021-11-28' AS Date), CAST(N'14:38:00' AS Time), NULL, NULL, 1, NULL, NULL)
INSERT [dbo].[OrderDetail] ([IDOrder], [IDProduct], [IDTable], [Quantity], [Note], [Reason], [Status]) VALUES (N'OD001', N'SP01', N'TB001', 1, N'', N'          ', 0)
INSERT [dbo].[OrderDetail] ([IDOrder], [IDProduct], [IDTable], [Quantity], [Note], [Reason], [Status]) VALUES (N'OD002', N'SP01', N'TB001', 1, N'', N'          ', 0)
INSERT [dbo].[OrderDetail] ([IDOrder], [IDProduct], [IDTable], [Quantity], [Note], [Reason], [Status]) VALUES (N'OD005', N'SP04', N'TB003', 1, N'', N'          ', 0)
INSERT [dbo].[OrderDetail] ([IDOrder], [IDProduct], [IDTable], [Quantity], [Note], [Reason], [Status]) VALUES (N'OD003', N'SP04', N'TB003', 1, N'', N'          ', 0)
INSERT [dbo].[OrderDetail] ([IDOrder], [IDProduct], [IDTable], [Quantity], [Note], [Reason], [Status]) VALUES (N'OD006', N'SP04', N'TB003', 1, N'', N'          ', 0)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP01', N'Cafe Sữa', 25000, N'cafesua.jpg', 0, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP02', N'Cafe Đen', 25000, N'cafeden.jpg', 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP03', N'Trà ĐÀo', 35000, N'daossua.jpg', 1, 2)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP04', N'Trà Chanh', 25000, NULL, 0, 2)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP05', N'Trà gừng', 25000, NULL, 1, 2)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP06', N'Trà đào cam xả', 35000, NULL, 1, 2)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP07', N'Bạc xỉu đá', 25000, NULL, 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP08', N'Bạc xỉu nóng', 25000, NULL, 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP09', N'Cafe Kem', 30000, NULL, 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP10', N'Cafe Cốt Dừa', 30000, NULL, 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP11', N'Cafe Đá Xay', 25000, NULL, 1, 1)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP12', N'Trà sữa Dâu Tây', 35000, NULL, 0, 4)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP13', N'Trà sữa Dâu Tây', 35000, NULL, 1, 3)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP14', N'Trang', 2000000, N'gai-xinh-1.jpg', 1, 6)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP15', N'Đào', 1000000, N'gai-xinh-mac-vay-ngan-15-750x937.jpg', 1, 7)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP16', N'Quỳnh', 500000, N'anh-girl-xinh-han-quoc-mac-vay-ngan.jpg', 1, 7)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP17', N'Bạch tuộc áp chảo', 1000000, N'bach-tuoc-nuong-1.jpg', 1, 8)
INSERT [dbo].[Product] ([IDProduct], [ProductName], [Price], [Image], [Status], [IDType]) VALUES (N'SP18', N'Cá sấu Pro', 100000, N'thit-ca-sau-9.jpg', 1, 9)
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (1, N'Cafe', NULL)
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (2, N'Trà', NULL)
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (3, N'Trà sữa', N'L')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (4, N'Trà sữa', N'M')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (5, N'Nước ép', NULL)
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (6, N'Cafe ôm', N'chân dài')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (7, N'Cafe ôm', N'chân ngon')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (8, N'Bạch tuộc', N'')
INSERT [dbo].[ProductType] ([IDType], [TypeName], [Size]) VALUES (9, N'Cá sấu ', N'')
SET IDENTITY_INSERT [dbo].[Promotions] ON 

INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description], [Status]) VALUES (1, N'Khai Trương', 50, CAST(N'2020-02-02' AS Date), CAST(N'2021-01-01' AS Date), N'Khai trương Giảm Giá', 0)
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description], [Status]) VALUES (2, N'Tết', 15, CAST(N'2020-03-01' AS Date), CAST(N'2021-03-01' AS Date), N'Sự Kiện Tết', 1)
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description], [Status]) VALUES (3, N'Voucher Thongpro', 90, CAST(N'2020-03-01' AS Date), CAST(N'2021-03-01' AS Date), N'follow thongpro để nhận mã giảm giá :D', 1)
INSERT [dbo].[Promotions] ([IDPromo], [NamePromo], [DiscountPromo], [StartPromo], [EndPromo], [Description], [Status]) VALUES (5, N'Mạnhpro', 5, CAST(N'2021-11-22' AS Date), CAST(N'2021-12-07' AS Date), N'Ae nhà pro', 1)
SET IDENTITY_INSERT [dbo].[Promotions] OFF
INSERT [dbo].[Table] ([IDTable], [Location], [Status], [IDArea], [TableGroup]) VALUES (N'TB001', 1, 0, 1, NULL)
INSERT [dbo].[Table] ([IDTable], [Location], [Status], [IDArea], [TableGroup]) VALUES (N'TB002', 2, 0, 1, NULL)
INSERT [dbo].[Table] ([IDTable], [Location], [Status], [IDArea], [TableGroup]) VALUES (N'TB003', 3, 0, 1, NULL)
INSERT [dbo].[Table] ([IDTable], [Location], [Status], [IDArea], [TableGroup]) VALUES (N'TB004', 4, 2, 1, NULL)
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Customer] FOREIGN KEY([IDCust])
REFERENCES [dbo].[Customer] ([IDCust])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Customer]
GO
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Employee] FOREIGN KEY([UsernameEMP])
REFERENCES [dbo].[Employee] ([UsernameEMP])
GO
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Employee]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Order] FOREIGN KEY([IDOrder])
REFERENCES [dbo].[Order] ([IDOrder])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Order]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Product] FOREIGN KEY([IDProduct])
REFERENCES [dbo].[Product] ([IDProduct])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Product]
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Table] FOREIGN KEY([IDTable])
REFERENCES [dbo].[Table] ([IDTable])
GO
ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Table]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_ProductType] FOREIGN KEY([IDType])
REFERENCES [dbo].[ProductType] ([IDType])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK_Product_ProductType]
GO
ALTER TABLE [dbo].[Table]  WITH CHECK ADD  CONSTRAINT [FK_Table_Area] FOREIGN KEY([IDArea])
REFERENCES [dbo].[Area] ([IDArea])
GO
ALTER TABLE [dbo].[Table] CHECK CONSTRAINT [FK_Table_Area]
GO
