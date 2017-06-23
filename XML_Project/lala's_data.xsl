<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:lala="http://lala's_data.com.tw">
	<xsl:output method="html" indent="yes"/>
	
	<xsl:template match="lala:各系啦啦資料">
		<!--<h1>各系啦啦資料</h1>-->
			<xsl:for-each select="lala:科系">
				<table border="1"><tr><th colspan="2"><xsl:value-of select="@系名"/></th></tr>
					<tr align="center"><td  colspan="2"><xsl:apply-templates select="lala:主題"/></td></tr>
					<tr align="center">
						<td>總預算:<xsl:value-of select="sum(lala:預算//@*)"/></td>
						<td>教練資料</td>
					</tr>
					<tr>						
						<td><xsl:apply-templates select="lala:預算"/></td>
						<td>
							<xsl:choose>
								<xsl:when test="lala:教練">
									<xsl:apply-templates select="lala:教練"/>
								</xsl:when>
								<xsl:otherwise>無教練</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>						
					<tr><td colspan="2" align="center"><xsl:apply-templates select="lala:隊員資料"/></td></tr>
					<tr>
						<td colspan="2"><xsl:apply-templates select="lala:隊員資料/lala:隊員"/></td>
					</tr>
				</table><br/>
			</xsl:for-each>
	</xsl:template>

	<xsl:template match="lala:主題">
		主題:<xsl:value-of select="."/>
	</xsl:template>
	<xsl:template match="lala:預算">
		教練預算:<xsl:value-of select="@教練預算"/><br/>
		服裝預算:<xsl:value-of select="@服裝預算"/><br/>
		道具預算:<xsl:value-of select="@道具預算"/><br/>
	</xsl:template>
	<xsl:template match="lala:教練">
		姓名:<xsl:value-of select="@姓名"/><br/>
		性別:<xsl:value-of select="@性別"/><br/>
		電話:<xsl:value-of select="@電話"/><br/>
		價錢:<xsl:value-of select="lala:價錢"/><br/>		
		車牌:<xsl:choose>
			<xsl:when test="lala:車牌">
				<xsl:apply-templates select="lala:車牌"/><br/>
			</xsl:when>
			<xsl:otherwise>無<br/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="lala:隊員資料">
		隊員資料  人數:<xsl:value-of select="count(lala:隊員)"/>
	</xsl:template>
	<xsl:template match="lala:隊員">
		姓名:<xsl:value-of select="@姓名"/><br/>
		班級:<xsl:value-of select="@班級"/><br/>
		學號:<xsl:value-of select="@學號"/><br/>
		電話:<xsl:value-of select="@電話"/><br/>
		住宿通勤:<xsl:value-of select="lala:住宿通勤"/><br/>	
		運動或其他專長:<xsl:value-of select="lala:運動或其他專長"/><br/>	
		肩長:<xsl:value-of select="lala:身材/@肩長"/> 
		胸圍:<xsl:value-of select="lala:身材/@胸圍"/> 
		腰圍:<xsl:value-of select="lala:身材/@腰圍"/> 
		臀圍:<xsl:value-of select="lala:身材/@臀圍"/> 
		腿長:<xsl:value-of select="lala:身材/@腿長"/><br/>
		體育課:<xsl:value-of select="lala:體育課"/> 
		節次:<xsl:value-of select="lala:體育課/@節次"/><br/>
		<hr/>
	</xsl:template>
	
</xsl:stylesheet>
