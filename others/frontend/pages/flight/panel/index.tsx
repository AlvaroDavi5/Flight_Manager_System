import { Scrollbars } from 'react-custom-scrollbars'
import {
	useColorModeValue, useDisclosure,
	Box, Flex, Button, IconButton, Input, Textarea,
	Modal, ModalOverlay, ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton
} from '@chakra-ui/react'
import { IoCreateOutline } from 'react-icons/io5'
import { FaTrashAlt, FaEdit } from 'react-icons/fa'
import DocumentHead from "@pages/components/document_head"
import Navbar from "@pages/components/navbar"


function TaskEditorModal(props: any) {
	return (
		<Modal
			isOpen={props.isOpen} onClose={props.onClose}
			size='xl' scrollBehavior='outside'
			blockScrollOnMount={true} closeOnOverlayClick={false}
		>
			<ModalOverlay/>
			<ModalContent bgColor={props.bgColor}>
				<ModalHeader>
					Criar/Editar Tarefa
				</ModalHeader>
				<ModalCloseButton/>

				<ModalBody>
					<Input type='text' maxLength={95} placeholder='Nome da tarefa' maxWidth='95%' marginTop='20px' marginLeft='10px' marginRight='10px' background='green.100'/><br/>
					<Input type='date' maxWidth='14vw' marginTop='20px' marginLeft='10%' background='green.100'/>
					<Input type='time' maxWidth='9vw' marginTop='20px' marginLeft='10%' background='green.100'/>
					<Textarea maxLength={350} placeholder='Descrição da tarefa' marginTop='20px' background='green.100'/>
				</ModalBody>

				<ModalFooter>
					<Box>
						<Button
							boxShadow='1px 1px 2px 2px rgba(0, 0, 0, 0.3)'
							variant='ghost'
							backgroundColor='green'
							marginRight='150'
							marginLeft='15'
						>
							Salvar
						</Button>
						<Button
							boxShadow='1px 1px 2px 2px rgba(0, 0, 0, 0.3)'
							variant='ghost'
							backgroundColor='red'
							marginLeft='150'
							marginRight='5'
							onClick={props.onClose}
						>
							Cancelar
						</Button>
					</Box>
				</ModalFooter>
			</ModalContent>
		</Modal>
	)
}

function ListItem(props: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const boxBgColor = (colorMode == 'light' ? 'marine' : 'primary')
	const nameContinue = (((props.name).toString()).length > 55) ? '...' : ''
	const descContinue = (((props.desc).toString()).length > 110) ? '...' : ''
	const date = new Date(props.date)
	const actualDate = new Date()

	const dateDiffInDays = (date.getTime() - actualDate.getTime()) / (1000 * 3600 * 24)
	let datePriorityColor = "blue"
	if (dateDiffInDays < 5) {
		datePriorityColor = "red"
	}
	else if (dateDiffInDays < 10) {
		datePriorityColor = "orange"
	}
	else if (dateDiffInDays > 0) {
		datePriorityColor = "green"
	}
	else {
		datePriorityColor = "blue"
	}

	function dateFormatter(date: number) {
		const strDate = date.toString()

		if (strDate.length == 1) {
			return '0' + strDate
		}
		else {
			return strDate
		}
	}

	return (
		<>
			<TaskEditorModal
				isOpen={props.isOpenEdit} onOpen={props.onOpenEdit} onClose={props.onCloseEdit}
				bgColor={boxBgColor}
			/>

			<Flex
				width='90vw'
				height='75px'
				boxShadow='1px 1px 10px 4px rgba(0, 0, 0, 0.3)'
				margin='20px'
				borderRadius='10px'
				bgColor={boxBgColor}
				flexDirection='row'
				justifyContent='space-between'
			>
				<Box
					backgroundColor={datePriorityColor}
					borderRadius='10px'
					height='20pt'
					width='110px'
					marginTop='25px'
					marginLeft='15px'
					justifyContent='center'
					alignContent='center'
					textAlign='center'
				>
					{
						` ${dateFormatter(date.getDate()+1)}
						/ ${dateFormatter(date.getMonth()+1)}
						/ ${date.getFullYear()} `
					}
				</Box>
				<Flex
					justifyContent='center'
					textAlign='center'
					flexDirection='column'
				>
					<Box
						margin='5px'
						fontWeight='bold'
						fontSize='14pt'
					>
						{((props.name.slice(0, 55)).toString()).concat(nameContinue)}
					</Box>
					<Box
						margin='6px'
					>
						{((props.desc.slice(0, 110)).toString()).concat(descContinue)}
					</Box>
				</Flex>
				<Flex
					position='relative'
				>
				<Button
					variant='ghost'
					color='black'
					marginTop='20px'
				>
					<FaEdit onClick={props.onOpenEdit}/>
				</Button>
				<Button
					variant='ghost'
					color='black'
					marginTop='20px'
					marginRight='10px'
				>
					<FaTrashAlt/>
				</Button>
				</Flex>
			</Flex>
		</>
	)
}

export default function TasksPage({ taskList }: any) {
	const colorMode = useColorModeValue('light', 'dark')
	const pageBgColor = (colorMode == 'light' ? 'clear_lake' : 'dark_forest')
	const boxBgColor = (colorMode == 'light' ? 'marine' : 'primary')
	const { isOpen, onOpen, onClose } = useDisclosure()

	function tasksRender() {
		const tasks = []

		for (let i = 0; i < taskList.length; i++) {
			tasks.push(
				<ListItem key={(taskList[i]).id}
					date={`${(taskList[i]).deadline_date}`}
					name={`${(taskList[i]).name}`} desc={`${(taskList[i]).description}`}
					isOpenEdit={isOpen} onOpenEdit={onOpen} onCloseEdit={onClose}
				/>
			)
		}

		return tasks
	}

	return (
		<body>
			<DocumentHead title="Tarefas"/>
			<Navbar pageName="Tarefas"/>
			<TaskEditorModal
				isOpen={isOpen} onOpen={onOpen} onClose={onClose}
				bgColor={boxBgColor}
			/>

			<Box
				w='100%'
				h='100%'
				position='fixed'
				backgroundColor={pageBgColor}
				justifyContent='space-between'
			>
				<Box
					width='100%'
				>
					<IconButton
						aria-label='add new task'
						variant='ghost'
						bgColor={boxBgColor}
						color='black'
						margin='10px'
						boxShadow='1px 1px 2px 2px rgba(0, 0, 0, 0.3)'
						onClick={onOpen}
					>
						<IoCreateOutline size='40px'/>
					</IconButton>
				</Box>
				<Scrollbars>
					<Box
						justifyContent='center'
						marginLeft='40px'
						marginRight='40px'
					>
						{tasksRender()}
						<Box
							backgroundColor={pageBgColor}
							width='90vw' height='120px'
							marginTop='40px' marginBottom='40px'
							marginLeft='20px' marginRight='20px'
						/>
					</Box>
				</Scrollbars>
			</Box>
		</body>
	)
}

export async function getServerSideProps(context: any) {
	const req = await fetch(`${process.env.APP_PORT}/api/flight/panel`)
	const tasksReq = await req.json()

	return {
		props: {
			taskList: tasksReq.data
		}
	}
}
