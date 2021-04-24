
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.entities.Guild;
import java.util.List;

public class JListener extends ListenerAdapter{

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor(); //보낸사람 이름
        TextChannel tc = event.getTextChannel(); //채팅방 이름
        Message msg = event.getMessage(); //보낸 메시지
        Guild guild = event.getGuild();
        String ownerName = guild.getOwner().getNickname(); //서버 주인장
        
        
        
        if(user.isBot()) return; //유저가 봇이면 버림
        
        /* !!!-----------------------------------------------------------
         * 들어온 것이 명령어일경우
         * !!!-----------------------------------------------------------*/
        if(msg.getContentRaw().charAt(0) == '!'){
            String[] args = msg.getContentRaw().substring(1).split(" ");
            if(args.length <= 0) return; //!뒤에 아무것도 없으면 버림
            
            //clear 기능
            if(args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")){
                msg.delete().queue();
                if(args.length != 2) return;
                int count = 1;
                try{ count = Integer.parseInt(args[1]);
                } catch(Exception e){
                    tc.sendMessage("정수를 입력해야합니다!").queue();
                    return;
                }
                if(count < 1 || count > 100){
                    tc.sendMessage("1에서 100사이의 정수를 입력해야합니다!").queue();
                    return;
                }
                MessageHistory mh = new MessageHistory(tc);
                List<Message> msgs = mh.retrievePast(count).complete();
                tc.deleteMessages(msgs).complete();
                tc.sendMessage("Deleted " + count + " messages").queue();
                
            }
            
            
            
            /* !!!-----------------------------------------------------------
             * 들어온 것이 일반 대화일경우
             * !!! --------------------------------------------------------*/
            
        } else {
       	
        	//언어감지
        	Translator trs = new Translator();
        	String lang = trs.whatLanguage(msg.getContentRaw());
        	
        	
        	if (lang.equals("ko")) { //한국어일경우
        		String message = trs.TransReturnKRtoJP(msg.getContentRaw());
        		System.out.println("Owner : " + guild.getOwner() + " / Sender : " + user + " / Original : " + msg.getContentRaw() + " / Trans : " + message);
        		tc.sendMessage(user.getAsMention() + " : " + message).queue();
        		
        		
        	} else if (lang.equals("ja")){ //일본어일경우
        		String message = trs.TransReturnJPtoKR(msg.getContentRaw());
        		System.out.println("Owner : " + guild.getOwner() + " / Sender : " + user + " / Original : " + msg.getContentRaw() + " / Trans : " + message);
        		tc.sendMessage(user.getAsMention() + " : " + message).queue();
        	}
        }
    }
}
        	
        	
