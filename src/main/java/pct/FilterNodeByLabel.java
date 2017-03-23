package pct;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import static org.neo4j.helpers.collection.MapUtil.stringMap;

public class FilterNodeByLabel
{
    // Only static fields and @Context-annotated fields are allowed in
    // Procedure classes. 
    
    // This field declares that we need a GraphDatabaseService
    // as context when any procedure in this class is invoked
    @Context
    public GraphDatabaseService db;

    // This gives us a log instance that outputs messages to the
    // standard log, normally found under `data/log/console.log`
    @Context
    public Log log;

    /**
     * It returns a Stream of Records, where records are
     * specified per procedure. This particular procedure returns
     * a stream of {@link SearchHit} records.
     *
     * The arguments to this procedure are annotated with the
     * {@link Name} annotation and define the position, name
     * and type of arguments required to invoke this procedure.
     * There is a limited set of types you can use for arguments,
     * these are as follows:
     *
     * <ul>
     *     <li>{@link String}</li>
     *     <li>{@link Long} or {@code long}</li>
     *     <li>{@link Double} or {@code double}</li>
     *     <li>{@link Number}</li>
     *     <li>{@link Boolean} or {@code boolean}</li>
     *     <li>{@link java.util.Map} with key {@link String} and value {@link Object}</li>
     *     <li>{@link java.util.List} of elements of any valid argument type, including {@link java.util.List}</li>
     *     <li>{@link Object}, meaning any of the valid argument types</li>
     * </ul>
     *
     * @param node - list of nodes to filter
     * @param label - target label to match
     * @return the nodes found by the query
     */
    @Procedure(value = "pct.FilterNodeByLabel", mode = Mode.READ)
    @Description("Filter nodes according to matching label")
    public Stream<SearchHit> search( @Name("node") Node _node,
                                     @Name("label") String _label )
    {
        SearchHit hit;        
        Label targetLabel = Label.label(_label);
        
        log.debug("----------------");
        log.debug("pct.FilterNodeByLabel");
        log.debug("Target label %s", targetLabel.toString());
        log.debug("%s node labels", _node.toString());        
        for(Label _thisLabel: _node.getLabels()) {
            
            if (_thisLabel.equals(targetLabel)) {
                log.debug("!!!!\t%s", _thisLabel.toString());
            } else {
                log.debug("\t%s", _thisLabel.toString());
            }
        }
        
        for(Label _thisLabel: _node.getLabels()) {
            
            if (_thisLabel.equals(targetLabel)) {

                hit = new SearchHit(_node);
                return Stream.of(hit);

            } 
        }

        return Stream.empty();
    }

    public static class SearchHit
    {
        public Node result;

        public SearchHit( Node node )
        {
            this.result = node;
        }
    }

}
