<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <xsl:element name="entries">
            <xsl:for-each
                    select="/*[name()='entries']/*[name()='entry']/*[name()='field']">
                <xsl:element name="entry">
                    <xsl:attribute name="field" >
                        <xsl:value-of select="."/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>