import { Flex, useColorModeValue } from '@chakra-ui/react'
import DocumentHead from "@pages/components/document_head"
import Navbar from "@pages/components/navbar"


export default function BibliographiesPage() {
	const colorMode = useColorModeValue('light', 'dark')
	const pageBgColor = (colorMode == 'light' ? 'clear_lake' : 'dark_forest')

	return (
		<body>
			<DocumentHead title="Bibliografias"/>
			<Navbar pageName="Consultas Bibliográficas"/>

			<Flex
				w='100%'
				h='100%'
				position='fixed'
				backgroundColor={pageBgColor}
				justifyContent='space-between'
			>
				<div>Bibliografias</div>
			</Flex>
		</body>
	)
}
