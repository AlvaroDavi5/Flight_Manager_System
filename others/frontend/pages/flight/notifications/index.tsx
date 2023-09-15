import React, { useEffect, useState } from 'react';
import io from "socket.io-client";
import { Flex, Box, useColorModeValue } from '@chakra-ui/react'
import DocumentHead from "@pages/components/document_head"
import Navbar from "@pages/components/navbar"


const websocketUrl = process.env.WEBSOCKET_URL || 'http://localhost:4001'
let socket = io(websocketUrl);

function Messages(props: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const boxBgColor = (colorMode == 'light' ? 'marine' : 'primary')

	return (
		<Flex
			w='90%'
			marginTop='10px'
			boxShadow='1px 1px 10px 10px rgba(0, 0, 0, 0.1)'
			borderRadius='10px'
			backgroundColor={boxBgColor}
			fontSize='x-large'
			textAlign='center'
			justifyContent='center'
			alignContent='center'
			alignItems='center'
		>
			<Box
				backgroundColor={'rgba(255, 0, 0, 0.5)'}
				borderRadius='10%'
				margin='5px'
				maxH='50%'
				maxW='50%'
			>
				<b>Topic:</b> {props.topic}
			</Box>
			<Box
				backgroundColor={'rgba(255, 255, 0, 0.5)'}
				borderRadius='10%'
				margin='5px'
				maxH='50%'
				maxW='50%'
			>
				<b>Key:</b> {props.messageKey}
			</Box>
			<Box
				backgroundColor={'rgba(255, 0, 255, 0.5)'}
				borderRadius='10%'
				margin='5px'
				maxH='50%'
				maxW='50%'
			>
				<b>FlightStatus:</b> {props.message.flightStatus}
			</Box>
			<Box
				backgroundColor={'rgba(0, 0, 255, 0.5)'}
				borderRadius='10%'
				margin='5px'
				maxH='50%'
				maxW='50%'
			>
				<b>LogisticStatus:</b> {props.message.logisticStatus}
			</Box>
		</Flex>
	)
}

export default function ProjectsPage(props: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const pageBgColor = (colorMode == 'light' ? 'clear_lake' : 'dark_forest')
	const msgs: any[] = []
	const [messages, setMessages] = useState(msgs)

	useEffect(() => {
		socketInitializer();

		return () => {
			socket.disconnect();
		};
	}, []);

	async function socketInitializer() {
		await fetch("/api/");

		socket = io(websocketUrl);

		socket.on('receivedMessage', (message) => {
			setMessages((prevMessages): any[] => [...prevMessages, message])
		});
	}

	return (
		<body>
			<DocumentHead title="Notificações" />
			<Navbar pageName="Notificações" />

			<Box
				w='100%'
				h='100%'
				position='fixed'
				backgroundColor={pageBgColor}
				justifyContent='space-between'
			>
				<Box
					h='40px'
					marginTop='25px'
					marginLeft='30px'
					marginRight='30px'
					padding='1px 10px 1px 10px'
					boxShadow='1px 1px 10px 10px rgba(0, 0, 0, 0.1)'
					borderRadius='10px'
					backgroundColor={'rgba(0, 255, 0, 0.1)'}
					fontSize='x-large'
					textAlign='center'
					justifyContent='center'
				>
					<b>Socket ID:</b> {socket.id}
				</Box>
				<Box>
					{messages.map((message) => {
						const data = JSON.parse(message)
						return (<Messages topic={data.topic} messageKey={data.message.key} message={data.message.value} />)
					})}
				</Box>
			</Box>
		</body>
	)
}
