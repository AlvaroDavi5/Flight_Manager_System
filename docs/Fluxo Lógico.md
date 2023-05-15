
# Fluxo Lógico Orientado a Eventos

### Torre de Controle
* Ao receber relatório da torre de controle
	> Verificar se é chegada ou partida
	- Se é chegada:
		> Verificar se a aeronave consta nos registros de tráfego aéreo
		- Se sim:
			> Verificar a disponibilidade dos portões
			- Se disponível:
				1. Alocar o portão mais afastado da pista que esteja livre
				> Verificar liberação da pista
				- Se liberada:
					1. Permitir pouso
					2. Fechar pista por um intervalo de tempo
					> Finalizado o intervalo de tempo, validar se a aeronave já pousou
					- Se sim:
						> Validar se a aeronave ainda está taxeando pela pista
						- Se sim:
							1. Manter pista fechada
						- Se não:
							1. Abrir pista
							2. Iniciar desembarque
							3. Iniciar embarque
							4. Solicitar decolagem
					- Se não:
						1. Rejeitar pouso
						2. Manter aeronave em espera por um intervalo de tempo
				- Se ocupada:
					1. Manter aeronave em espera por um intervalo de tempo
					2. Verificar liberação da pista novamente
			- Se indisponível:
					1. Manter aeronave em espera por um intervalo de tempo
					2. Verificar disponibilidade dos portões novamente
		- Se não:
			1. Rejeitar pouso
	- Se é partida:
		> Verificar liberação da pista
		- Se liberada:
			1. Permitir decolagem da aeronave
		- Se ocupada:
			1. Manter aeronave pronta para decolagem
			2. Verificar liberação da pista novamente

### Tráfego Aéreo
* Ao receber relatório de tráfego aéreo
	- Salvar dados meteorológicos em cache
	- Salvar códigos de vôo em cache
	- Criar vôo programado no banco de dados

Para cada mudança de status importante, novos eventos são gerados e enviados para os tópicos de logística e notificação.

---

## Fluxo Lógico com Base nos Status

`LogisticStatus`, **FlightStatus** e _PanelStatus_

- **SCHEDULED**
	- _SCHEDULED_
	- `REQUESTING_LAND`
		- `REJECTED_TO_LAND`
			- **HOLDING**
				- `ALLOWED_TO_LAND`
				- `DIVERTED`
					- **CANCELLED**
					- _CANCELLED_
		- `ALLOWED_TO_LAND`
			- `TAXIING`
				- **ARRIVED**
			- `REQUESTING_MAINTENANCE`
				1. **GROUNDED**
				2. `MAINTENANCE`
				3. **READY**
			- `LANDED`
				1. **OPENED**
					1. _DEBOARDING_
					2. _BOARDING_
				2. **CLOSED**
					- _CLOSED_
				3. **LOADING**
				- **READY**
					- `REQUESTING_TAKEOFF`
				- **CONCLUDED**
	- _ON_TIME_
	- _DELAYED_
- **READY**
	- `REQUESTING_TAKEOFF`
		- `REJECTED_TO_TAKEOFF`
			- **READY**
		- `ALLOWED_TO_TAKEOFF`
			- `TAXIING`
				- **DEPARTED**
			- `TAKED_OFF`
				- **IN_TRANSIT**
					- `REQUESTING_LAND`
				- _IN_TRANSIT_
