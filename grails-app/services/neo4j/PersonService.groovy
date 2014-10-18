package neo4j

import javax.transaction.Transactional;



class PersonService {

	def PersonRepository
	def Neo4jTemplate
	def SkillsRepository

	def savePerson(def data){

		println data
		Person person=new Person()
		person.setName(data.name[0])
		PersonRepository.save(person)

		data.skills.each  {
			def technicalSkill=SkillsRepository.findBySkills(it)

			Relationship relationship=new Relationship();
			relationship.setPerson(person)
			relationship.setSkills(technicalSkill)
			Neo4jTemplate.save(relationship);
		}
		return "person saved sucessfully"
	}
	def saveSkills(def data){
		println data

		if(data.technicalSkills!=null && data.technicalSkills!='blank') {
			println "inside technical skills"
			def technicalSkill=SkillsRepository.findBySkills(data.technicalSkills)

			if(technicalSkill!=null){
				log.debug "skills already exist"
			}
			else{
				println "***"
				Skills skills=new Skills()
				skills.setSkills(data.technicalSkills)
				println skills
				SkillsRepository.save(skills)
			}
		}

		if(data.technicalSkills1!=null && data.technicalSkills1!='blank') {
			def technicalSkill=SkillsRepository.findBySkills(data.technicalSkills1)
			if(technicalSkill!=null)
				log.debug"skills already exist"
			else{

				Skills skills=new Skills()
				skills.setSkills(data.technicalSkills1)
				SkillsRepository.save(skills)
			}
		}

		if(data.technicalSkills2!=null && data.technicalSkills2!='blank' ) {
			def technicalSkill=SkillsRepository.findBySkills(data.technicalSkills2)
			if(technicalSkill!=null)
				log.debug"skills already exist"
			else{
				Skills skills=new Skills()
				skills.setSkills(data.technicalSkills2)
				SkillsRepository.save(skills)
			}
		}
		return "skills saved sucessfully"
	}

	@Transactional
	def getSkills(){
		def skills=	SkillsRepository.findAll()
		println skills

		List list=[]
		skills.each {
			Map map=[:]
			map.skills=it.skills
			list.push(map)
		}
		return list
	}


	@Transactional
	def getPersons(){

		List list=[]
		PersonRepository.findAll().each{
			Map persons=[:]
			persons.name=it.name
			persons.skills=Neo4jTemplate.fetch(it.skills).skills
			list.push(persons)
		}
		return list
	}
}