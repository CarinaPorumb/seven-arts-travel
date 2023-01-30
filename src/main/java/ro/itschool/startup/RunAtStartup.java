package ro.itschool.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.ArtEvent;
import ro.itschool.entity.ArtObject;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.enums.Category;
import ro.itschool.enums.Style;
import ro.itschool.service.ArtEventService;
import ro.itschool.service.ArtObjectService;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;
    @Autowired
    private ArtObjectService artObjectService;
    @Autowired
    private ArtEventService artEventService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        saveAdmin();
        //      saveContributor();
        saveUser();
        saveArtObject();
        saveArtEvent();
    }

    public void saveAdmin() {
        User user = new User();
        user.setUsername("Admin");
        user.setPassword("Admin");
        user.setRandomToken("randomToken");
        final Role roleUserAdmin = new Role("ROLE_ADMIN");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleUserAdmin);
        user.setRoles(roles);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("admin@gmail.com");
        user.setFullName("Admin1");
        user.setPasswordConfirm("Admin");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveContributor() {
        User user = new User();
        user.setUsername("Contributor");
        user.setPassword("Contributor");
        user.setRandomToken("randomToken");
        final Set<Role> roleUserContributor = new HashSet<>();
        roleUserContributor.add(new Role("ROLE_CONTRIBUTOR"));
        user.setRoles(roleUserContributor);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("contributor@gmail.com");
        user.setFullName("Contributor1");
        user.setPasswordConfirm("Contributor");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveUser() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("User");
        user.setRandomToken("randomToken");
        final Set<Role> roleUserUser = new HashSet<>();
        roleUserUser.add(new Role("ROLE_USER"));
        user.setRoles(roleUserUser);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEmail("user@gmail.com");
        user.setFullName("User");
        user.setPasswordConfirm("User");
        user.setRandomTokenEmail("randomToken");
        userService.saveUser(user);
    }

    public void saveArtObject() {
        Set<ArtObject> artObjectSet = new HashSet<>();
        ArtObject architecture1 = new ArtObject();
        architecture1.setName("Chateau de Chambord");
        architecture1.setImageLink("https://royal-connection.fr/wp-content/uploads/2017/05/excursion-chateau-chambord.jpg");
        architecture1.setAuthor("Domenico da Cortona");
        architecture1.setYear(1547);
        architecture1.setCategory(Category.ARCHITECTURE);
        architecture1.setMovement(Style.RENAISSANCE);
        architecture1.setIsTemporary(false);
        architecture1.setLocation("Chateau, 41250, Chambord, France");

        ArtObject architecture2 = new ArtObject();
        architecture2.setName("Notre-Dame de Paris");
        architecture2.setImageLink("https://www.piatraonline.ro/userfiles/19370a59-92cf-454a-b230-f7886006c901/Image/2_v120.jpg");
        architecture2.setAuthor("Jean de Chelles");
        architecture2.setYear(1345);
        architecture2.setCategory(Category.ARCHITECTURE);
        architecture2.setMovement(Style.GOTHIC);
        architecture2.setIsTemporary(false);
        architecture2.setLocation("Notre-Dame, 75004, Paris, France");

        ArtObject architecture3 = new ArtObject();
        architecture3.setName("Casa Batllo");
        architecture3.setImageLink("https://www.artmajeur.com/medias/hd/r/a/radu-bercan/artwork/13405976_dsc05184af.jpg");
        architecture3.setAuthor("Antoni Gaudí");
        architecture3.setYear(1912);
        architecture3.setCategory(Category.ARCHITECTURE);
        architecture3.setMovement(Style.ARTNOUVEAU);
        architecture3.setIsTemporary(false);
        architecture3.setLocation("Pg. de Gracia, 43, 08007, Barcelona, Spania");

        ArtObject architecture4 = new ArtObject();
        architecture4.setName("Palacio da Pena");
        architecture4.setImageLink("https://tourscanner.com/blog/wp-content/uploads/2019/05/Pena-Palace-tickets-1.png");
        architecture4.setAuthor("Wilhelm Ludwig von Eschwege, Nicolau Pires");
        architecture4.setYear(1854);
        architecture4.setCategory(Category.ARCHITECTURE);
        architecture4.setMovement(Style.ROMANTICISM);
        architecture4.setIsTemporary(false);
        architecture4.setLocation("Estrada da Pena, 2710, Sintra, Portugal");

        ArtObject architecture5 = new ArtObject();
        architecture5.setName("Chateau d'Amboise");
        architecture5.setImageLink("https://www.loirevalley-france.co.uk/sites/default/files/visionneuse/image/amboise_7663_l_de_serres.jpg");
        architecture5.setAuthor("Domenico da Cortona, Fra Giocondo");
        architecture5.setYear(1495);
        architecture5.setCategory(Category.ARCHITECTURE);
        architecture5.setMovement(Style.RENAISSANCE);
        architecture5.setIsTemporary(false);
        architecture5.setLocation("37400 Amboise, France");

        ArtObject architecture6 = new ArtObject();
        architecture6.setName("Le Mont Saint-Michel Abbey");
        architecture6.setImageLink("https://www.rfi.ro/sites/default/files/styles/inside_content/public/articol/st_michel.png?itok=ckUJKeDT");
        architecture6.setAuthor("William of Volpiano");
        architecture6.setYear(1523);
        architecture6.setCategory(Category.ARCHITECTURE);
        architecture6.setMovement(Style.GOTHIC);
        architecture6.setIsTemporary(false);
        architecture6.setLocation("Le Mont Saint-Michel, Franța");

        ArtObject sculpture1 = new ArtObject();
        sculpture1.setName("The Thinker");
        sculpture1.setImageLink("http://www.oceanlight.com/stock-photo/le-penseur-musee-rodin-paris-picture-28173-741166.jpg");
        sculpture1.setAuthor("Auguste Rodin");
        sculpture1.setYear(1902);
        sculpture1.setCategory(Category.SCULPTURE);
        sculpture1.setMovement(Style.REALISM);
        sculpture1.setIsTemporary(false);
        sculpture1.setLocation("77 Rue de Varenne, 75007 Paris, France");

        ArtObject sculpture2 = new ArtObject();
        sculpture2.setName("Bird in Space");
        sculpture2.setImageLink("https://collectionapi.metmuseum.org/api/collection/v1/iiif/486757/1004909/restricted");
        sculpture2.setAuthor("Constantin Brancusi");
        sculpture2.setYear(1923);
        sculpture2.setCategory(Category.SCULPTURE);
        sculpture2.setMovement(Style.MODERNISM);
        sculpture2.setIsTemporary(false);
        sculpture2.setLocation("1000 5th Ave, New York, 10028, USA");

        ArtObject sculpture3 = new ArtObject();
        sculpture3.setName("Dancing With Dandelions");
        sculpture3.setImageLink("https://www.udesign.es/wp-content/uploads/2019/04/UD-SCULTURES-2.jpg");
        sculpture3.setAuthor("Robin Wight");
        sculpture3.setYear(2014);
        sculpture3.setCategory(Category.SCULPTURE);
        sculpture3.setMovement(Style.CONTEMPORARY);
        sculpture3.setIsTemporary(false);
        sculpture3.setLocation("Staffordshire, UK");

        ArtObject sculpture4 = new ArtObject();
        sculpture4.setName("Nefertiti Bust");
        sculpture4.setImageLink("https://upload.wikimedia.org/wikipedia/commons/1/1f/Nofretete_Neues_Museum.jpg");
        sculpture4.setAuthor("Thutmose");
        sculpture4.setYear(1345);
        sculpture4.setCategory(Category.SCULPTURE);
        sculpture4.setMovement(Style.ANCIENT);
        sculpture4.setIsTemporary(false);
        sculpture4.setLocation("Neues Museum Berlin, Germany");

        ArtObject museum1 = new ArtObject();
        museum1.setName("L'Annonciade Museum");
        museum1.setImageLink("https://static.seetheworld.com/image_uploader/photos/b1/original/enjoying-art-at-musee-de-l-annonciade-saint-tropez-saint-tropez.jpg");
        museum1.setAuthor("Georges Grammont");
        museum1.setYear(1922);
        museum1.setCategory(Category.PAINTING);
        museum1.setMovement(Style.MODERNISM);
        museum1.setIsTemporary(false);
        museum1.setLocation("Georges Grammont, 83990 Saint-Tropez, France");

        ArtObject museum2 = new ArtObject();
        museum2.setName("Art Safari");
        museum2.setImageLink("https://www.artsafari.ro/wp-content/uploads/2022/08/cover-editia-10.png");
        museum2.setAuthor("Pavilionului de Artă București");
        museum2.setYear(2023);
        museum2.setCategory(Category.PAINTING);
        museum2.setMovement(Style.CONTEMPORARY);
        museum2.setIsTemporary(true);
        museum2.setLocation("Strada Lipscani 18, București, 030167 Romania");

        ArtObject museum3 = new ArtObject();
        museum3.setName("The Louvre Museum");
        museum3.setImageLink("https://res.klook.com/images/fl_lossy.progressive,q_65/c_fill,w_1295,h_720/w_80,x_15,y_15,g_south_west,l_Klook_water_br_trans_yhcmh3/activities/djvwelyhrz8z9ufftnyk/PriorityAccessEntranceTickettotheLouvreMuseum.webp");
        museum3.setAuthor("Pierre Lescot");
        museum3.setYear(1793);
        museum3.setCategory(Category.PAINTING);
        museum3.setMovement(Style.RENAISSANCE);
        museum3.setIsTemporary(false);
        museum3.setLocation("Rue de Rivoli, 75001 Paris, France");

        ArtObject museum4 = new ArtObject();
        museum4.setName("Rijksmuseum");
        museum4.setImageLink("https://winterfestivalamsterdam.com/wp-content/uploads/2021/08/Rijksmuseum-John-Lewis-Marshall-scaled.jpg");
        museum4.setAuthor("Pierre Cuypers");
        museum4.setYear(1808);
        museum4.setCategory(Category.PAINTING);
        museum4.setMovement(Style.BAROQUE);
        museum4.setIsTemporary(false);
        museum4.setLocation("Museumstraat 1, 1071 XX Amsterdam, Netherlands");

        ArtObject museum5 = new ArtObject();
        museum5.setName("British Museum");
        museum5.setImageLink("https://youimg1.tripcdn.com/target/100s13000000u3nqm8913.jpg?proc=source%2Ftrip");
        museum5.setAuthor("Robert Smirke, J. R. Pope, J. J. Burnet, John Taylor");
        museum5.setYear(1753);
        museum5.setCategory(Category.PAINTING);
        museum5.setMovement(Style.RENAISSANCE);
        museum5.setIsTemporary(false);
        museum5.setLocation("Great Russell St, London WC1B, UK");

        ArtObject library1 = new ArtObject();
        library1.setName("Livraria Lello & Irmão");
        library1.setImageLink("https://static.saltinourhair.com/wp-content/uploads/2021/05/12103516/Livraria-Lello-porto2.jpg");
        library1.setAuthor(" Francisco Xavier Esteves");
        library1.setYear(1881);
        library1.setCategory(Category.LITERATURE);
        library1.setMovement(Style.ARTNOUVEAU);
        library1.setIsTemporary(false);
        library1.setLocation("R. das Carmelitas 144, 4050-161 Porto, Portugal");

        ArtObject library2 = new ArtObject();
        library2.setName("Strahov Library");
        library2.setImageLink("https://wecityguide.s3.amazonaws.com/wp-content/uploads/2020/03/08105134/Interior-of-The-Clementinum-and-the-National-Library.jpeg");
        library2.setAuthor("Giovanni Dominik Orsi");
        library2.setYear(1679);
        library2.setCategory(Category.LITERATURE);
        library2.setMovement(Style.GOTHIC);
        library2.setIsTemporary(false);
        library2.setLocation("Strahovské nádvoří 132/1, 118 00 Praha, Czech Republic");


        ArtObject library3 = new ArtObject();
        library3.setName("Library of Trinity College Dublin");
        library3.setImageLink("https://images.unsplash.com/photo-1505664194779-8beaceb93744?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80");
        library3.setAuthor("Thomas Burgh");
        library3.setYear(1592);
        library3.setCategory(Category.LITERATURE);
        library3.setMovement(Style.RENAISSANCE);
        library3.setIsTemporary(false);
        library3.setLocation("College Street, Dublin 2, Ireland");

        ArtObject library4 = new ArtObject();
        library4.setName("Royal Portuguese Cabinet of Reading");
        library4.setImageLink("https://media.cntraveler.com/photos/59cdea7d4c1bc3060f9b34a0/master/w_1600%2Cc_limit/Royal-Portuguese-Reading-Room-GettyImages-530795685.jpg");
        library4.setAuthor("Rafael da Silva Castro");
        library4.setYear(1837);
        library4.setCategory(Category.LITERATURE);
        library4.setMovement(Style.GOTHIC);
        library4.setIsTemporary(false);
        library4.setLocation("R. Luís de Camões, 30, Rio de Janeiro, Brazilia");


        try {
            artObjectService.save(architecture1);
            artObjectService.save(architecture2);
            artObjectService.save(architecture3);
            artObjectService.save(architecture4);
            artObjectService.save(architecture5);
            artObjectService.save(architecture6);
            artObjectService.save(sculpture1);
            artObjectService.save(sculpture2);
            artObjectService.save(sculpture3);
            artObjectService.save(sculpture4);
            artObjectService.save(museum1);
            artObjectService.save(museum2);
            artObjectService.save(museum3);
            artObjectService.save(museum4);
            artObjectService.save(museum5);
            artObjectService.save(library1);
            artObjectService.save(library2);
            artObjectService.save(library3);
            artObjectService.save(library4);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        artObjectSet.add(architecture1);
        artObjectSet.add(architecture2);
        artObjectSet.add(architecture3);
        artObjectSet.add(architecture4);
        artObjectSet.add(architecture5);
        artObjectSet.add(architecture6);
        artObjectSet.add(sculpture1);
        artObjectSet.add(sculpture2);
        artObjectSet.add(sculpture3);
        artObjectSet.add(sculpture4);
        artObjectSet.add(museum1);
        artObjectSet.add(museum2);
        artObjectSet.add(museum3);
        artObjectSet.add(museum4);
        artObjectSet.add(museum5);
        artObjectSet.add(library1);
        artObjectSet.add(library2);
        artObjectSet.add(library3);
        artObjectSet.add(library4);

    }

    public void saveArtEvent() {
        Set<ArtEvent> artEventSet = new HashSet<>();
        ArtEvent balletAndTheatre1 = new ArtEvent();
        balletAndTheatre1.setName("Swan Lake");
        balletAndTheatre1.setImageLink("https://i1.sndcdn.com/artworks-000611708353-qh2fmo-t500x500.jpg");
        balletAndTheatre1.setCategory(Category.BALLET_AND_THEATRE);
        balletAndTheatre1.setMovement(Style.ROMANTICISM);
        balletAndTheatre1.setLocation("London Coliseum, St Martin's Ln, London, UK");
        balletAndTheatre1.setEventTime(LocalDateTime.of(2023, 6, 2, 20, 0));
        balletAndTheatre1.setIsTemporary(true);

        ArtEvent balletAndTheatre2 = new ArtEvent();
        balletAndTheatre2.setName("The Phantom of the Opera");
        balletAndTheatre2.setImageLink("https://cdn.londonandpartners.com/-/media/images/london/visit/whats-on/theatre/the-phantom-of-the-opera/poto_visit-london_1920x1080.jpg?mw=640&hash=69678390596E8321E25E05220FC15549DCE3E6AB");
        balletAndTheatre2.setCategory(Category.BALLET_AND_THEATRE);
        balletAndTheatre2.setMovement(Style.ROMANTICISM);
        balletAndTheatre2.setLocation("Her Majesty's Theatre, London, UK");
        balletAndTheatre2.setEventTime(LocalDateTime.of(2022, 9, 2, 19, 30));
        balletAndTheatre2.setIsTemporary(true);

        ArtEvent music1 = new ArtEvent();
        music1.setName("Ludovico Einaudi - La Seine Musicale");
        music1.setImageLink("https://www.laseinemusicale.com/app/uploads/2022/06/ludovico-einaudi-la-seine-musicale-2.png");
        music1.setCategory(Category.MUSIC);
        music1.setMovement(Style.CONTEMPORARY);
        music1.setLocation("La Seine Musicale, Île Seguin, Boulogne-Billancourt, Franța");
        music1.setEventTime(LocalDateTime.of(2023, 10, 14, 20, 0));
        music1.setIsTemporary(true);

        ArtEvent music2 = new ArtEvent();
        music2.setName("George Enescu International Festival");
        music2.setImageLink("https://www.ziarulmetropolis.ro/wp-content/uploads/2020/10/slide-1.jpg");
        music2.setCategory(Category.MUSIC);
        music2.setMovement(Style.CONTEMPORARY);
        music2.setLocation("Romanian Athenaeum, 050204, Bucharest, Romania");
        music2.setEventTime(LocalDateTime.of(2023, 9, 4, 20, 0));
        music2.setIsTemporary(true);

        ArtEvent music3 = new ArtEvent();
        music3.setName("Mendelssohn’s Italian");
        music3.setImageLink("https://i2-prod.manchestereveningnews.co.uk/incoming/article20728742.ece/ALTERNATES/s1200b/1_Capture-2JPG.jpg");
        music3.setCategory(Category.MUSIC);
        music3.setMovement(Style.ROMANTICISM);
        music3.setLocation("The Bridgewater Hall, Manchester, UK");
        music3.setEventTime(LocalDateTime.of(2023, 10, 16, 16, 0));
        music3.setIsTemporary(true);

        ArtEvent music4 = new ArtEvent();
        music4.setName("Vienna New Year's Concert");
        music4.setImageLink("https://pbs.twimg.com/media/Ex4n24YWYAIAskV.jpg:large");
        music4.setCategory(Category.MUSIC);
        music4.setMovement(Style.ROMANTICISM);
        music4.setLocation("Golden Hall of the Vienna State Opera, Austria");
        music4.setEventTime(LocalDateTime.of(2024, 1, 1, 11, 15));
        music4.setIsTemporary(true);

        ArtEvent music5 = new ArtEvent();
        music5.setName("Festival-Tournee");
        music5.setImageLink("https://nhaccodien.vn/wp-content/uploads/2019/01/Berlin-Philharmonic.jpg");
        music5.setCategory(Category.MUSIC);
        music5.setMovement(Style.ROMANTICISM);
        music5.setLocation("Herbert-von-Karajan-Str. 1, 10785 Berlin, Germany");
        music5.setEventTime(LocalDateTime.of(2022, 9, 1, 19, 30));
        music5.setIsTemporary(true);

        ArtEvent cinema1 = new ArtEvent();
        cinema1.setName("Cannes Film Festival");
        cinema1.setImageLink("https://malaya.com.ph/wp-content/uploads/2020/03/Cannes.jpg");
        cinema1.setCategory(Category.CINEMA);
        cinema1.setMovement(Style.CONTEMPORARY);
        cinema1.setLocation("Palace of Festivals and Congresses of Cannes, France");
        cinema1.setEventTime(LocalDateTime.of(2023, 5, 16, 20, 0));
        cinema1.setIsTemporary(true);

        ArtEvent cinema2 = new ArtEvent();
        cinema2.setName("The National Museum of Cinema");
        cinema2.setImageLink("https://c8.alamy.com/comp/HGNBM9/tourists-visit-national-museum-of-cinema-in-turin-italy-HGNBM9.jpg");
        cinema2.setCategory(Category.CINEMA);
        cinema2.setMovement(Style.CONTEMPORARY);
        cinema2.setLocation("Via Montebello, 20, 10124 Torino, Italy");
        cinema2.setEventTime(LocalDateTime.of(1958, 1, 1, 20, 0));
        cinema2.setIsTemporary(false);

        try {
            artEventService.save(balletAndTheatre1);
            artEventService.save(balletAndTheatre2);
            artEventService.save(music1);
            artEventService.save(music2);
            artEventService.save(music3);
            artEventService.save(music4);
            artEventService.save(music5);
            artEventService.save(cinema1);
            artEventService.save(cinema2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        artEventSet.add(balletAndTheatre1);
        artEventSet.add(balletAndTheatre2);
        artEventSet.add(music1);
        artEventSet.add(music2);
        artEventSet.add(music3);
        artEventSet.add(music4);
        artEventSet.add(music5);
        artEventSet.add(cinema1);
        artEventSet.add(cinema2);
    }
}