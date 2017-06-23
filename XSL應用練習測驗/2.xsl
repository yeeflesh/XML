<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:am="http://answer_machine.csie.mcu.edu.tw">
	<xsl:output method="html" indent="yes"/>

	<xsl:template match="am:答錄機">
		<table border="2"><tr><th>來電電話</th><th>來電時間</th><th>來電內容</th></tr>
			<xsl:apply-templates select="am:內容"/>
		</table>
	</xsl:template>
	
	<xsl:template match="am:內容">
		<tr>
			<td><xsl:value-of select="@來電電話"/></td>
			<td>
				<xsl:value-of select="@年"/>/
				<xsl:value-of select="@月"/>/
				<xsl:value-of select="@日"/> ： 
				<xsl:value-of select="@時"/>
			</td>
			<td><xsl:value-of select="."/></td>
		</tr>
	</xsl:template>
	
</xsl:stylesheet>
