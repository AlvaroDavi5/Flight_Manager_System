
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
				1. Rejeitar pouso
		- Se não:
			1. Rejeitar pouso
	- Se é partida:
		> Verificar liberação da pista
		- Se liberada:
			1. Permitir decolagem da aeronave
		- Se ocupada:
			1. Manter aeronave pronta para decolagem

### Tráfego Aéreo
* Ao receber relatório de tráfego aéreo
	- Salvar dados meteorológicos em cache
	- Salvar códigos de vôo em cache
	- Criar vôo programado no banco de dados

---

## Fluxo Lógico com Base nos Status

...
