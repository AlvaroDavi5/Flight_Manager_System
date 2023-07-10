import { Flex, Box, Image, useColorModeValue } from '@chakra-ui/react'
import DocumentHead from "../components/document_head"
import Navbar from "../components/navbar"
import style from "./style/home.module.css"


function Card(props: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const boxBgColor = (colorMode == 'light' ? 'marine' : 'primary')

	return (
		<Box
			h='410px'
			w='300px'
			marginTop='60px'
			marginLeft='50px'
			marginRight='50px'
			boxShadow='1px 1px 10px 10px rgba(0, 0, 0, 0.1)'
			borderRadius='20px'
			backgroundColor={boxBgColor}
			fontSize='xx-large'
			textAlign='center'
			justifyContent='center'
		>
			<a
				href={props.pageHref}
			>
				<Image alt={`${props.children} image`}
					borderRadius='20px'
					width='94%'
					height='70%'
					marginTop='3%'
					marginLeft='3%'
					src={props.imgSource}
				/>
				<Box
					marginTop='15px'
				>
					{props.children}
				</Box>
			</a>
		</Box>
	)
}

export default function Home(props: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const pageBgColor = (colorMode == 'light' ? 'clear_lake' : 'dark_forest')

	return (
		<body className={style.pagebody}>
			<DocumentHead title="Início" />
			<Navbar pageName="Homepage" />

			<Flex
				width='100%'
				height='100%'
				position='fixed'
				backgroundColor={pageBgColor}
				justifyContent='space-between'
			>
				<Card pageHref={`/flight/panel`} imgSource="./assets/panel.jpg">
					Painel de Vôos
				</Card>
				<Card pageHref={`/flight/data`} imgSource="./assets/analitcs.png">
					Fluxo de Dados da Torre
				</Card>
				<Card pageHref={`/flight/notifications`} imgSource="./assets/notification.png">
					Notificações de Vôo
				</Card>
			</Flex>
		</body>
	)
}
